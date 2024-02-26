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
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AtomicDeconstructor extends Enchantment {
    public AtomicDeconstructor() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 16 + 14 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 40;
    }

    public static void handleEnchant(LivingAttackEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {

            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
            LivingEntity victim = fEvent.getEntityLiving();

            if (attacker != null) {
                ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
                int level = EnchantmentHelper.getEnchantmentLevel(Registry.ATOMIC_DECONSTRUCTOR.get(), dmgSource);
                if (level > 0) {
                    if (victim.getRNG().nextInt(3000) < level * 3) {
                        fEvent.getEntityLiving().attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
                    }
                }
            }
        }
    }
}