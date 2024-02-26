package dev.infernal_coding.flamesenchants.enchantments.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Levitator extends Enchantment {
    public Levitator() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 30;
    }


    public void onEntityDamaged(LivingEntity user, Entity entiti, int level) {
        if (entiti instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) entiti;
            entity.addPotionEffect(new EffectInstance(Effects.LEVITATION, 30 + level * 12, 1 + level));
        }
    }
}
