package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.Random;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class CriticalStrike extends Enchantment {

    static Random rand = new Random();

    public CriticalStrike() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof LuckMagnification);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 30;
    }


    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void onCritical(CriticalHitEvent e) {
        if (e.getPlayer() != null) {
            PlayerEntity player = e.getPlayer();
            ItemStack stack = Util.getHeldItem(player.getActiveHand(), player);
            int level = EnchantmentHelper.getEnchantmentLevel(Registry.CRITICAL_STRIKE.get(), stack);
            if (level > 0) {
                CompoundNBT nbt = stack.getOrCreateTag();

                int counter = nbt.getInt("failedCritCount");
                int maxReduction = level >= 4 ? 200 : 0;
                if (player.getRNG().nextInt(1000 - maxReduction) >= 32 * (counter + 1)) {
                    nbt.putInt("failedCritCount", counter + 1);
                } else {
                    nbt.putInt("failedCritCount", 0);
                    boolean bool = e.getTarget() instanceof LivingEntity;
                    float crit = 1.2F + (float) level * 0.2F + rand.nextFloat() * 0.5F;

                    e.setDamageModifier(crit);
                    if (e.isVanillaCritical()) {
                        e.setDamageModifier(e.getDamageModifier() * 1.35F);
                    } else {
                        e.setResult(Event.Result.ALLOW);
                    }

                }
            }
        }
    }
}