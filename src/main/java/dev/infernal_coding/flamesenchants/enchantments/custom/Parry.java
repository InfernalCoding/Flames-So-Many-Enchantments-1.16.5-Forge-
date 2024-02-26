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
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Parry extends Enchantment {
    public Parry() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 20 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 40;
    }

    public static void handleEnchant(LivingAttackEvent e) {
        if (e.getSource().getTrueSource() instanceof LivingEntity) {
            if (e.getEntity() instanceof LivingEntity) {
                LivingEntity victim = e.getEntityLiving();
                if (victim != null) {
                    ItemStack weaponSword = Util.getHeldItem(victim.getActiveHand(), victim);
                    LivingEntity attacker = (LivingEntity) e.getSource().getTrueSource();
                    if (attacker != null) {
                        int levelParry = EnchantmentHelper.getEnchantmentLevel(Registry.PARRY.get(), weaponSword);

                        if (levelParry> 0) {
                            if (victim.getRNG().nextInt(100) < 16 + levelParry * 8) {

                                attacker.playSound(SoundEvents.ENTITY_BAT_TAKEOFF, 0.3F, 3.0F);
                                e.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
