package dev.infernal_coding.flamesenchants.enchantments.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Envenomed extends Enchantment {
    public Envenomed() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public int getMinEnchantability(int par1) {
        return 16 + 12 * (par1 - 1);
    }

    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 30;
    }

    public void onEntityDamaged(LivingEntity user, Entity entiti, int level) {
        if (entiti instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) entiti;
            entity.addPotionEffect(new EffectInstance(Effects.POISON, 30 + level * 10, level - 1));
            entity.addPotionEffect(new EffectInstance(Effects.WITHER, 30 + level * 10, level));
        }
    }
}
