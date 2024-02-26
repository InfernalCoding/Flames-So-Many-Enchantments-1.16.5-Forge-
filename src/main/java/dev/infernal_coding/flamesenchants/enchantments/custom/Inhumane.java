package dev.infernal_coding.flamesenchants.enchantments.custom;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Inhumane extends Enchantment {
    public Inhumane() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 40;
    }


    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof DamageEnchantment);
    }

    @Override
    public boolean canApply(ItemStack fTest) {
        return fTest.getItem() instanceof AxeItem || super.canApply(fTest);
    }

    public void onEntityDamaged(LivingEntity user, Entity entiti, int level) {
        if (entiti instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) entiti;
            if (entity.getCreatureAttribute() == CreatureAttribute.ILLAGER) {
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 70 + level * 10, 1));
            }

        }
    }

    @Override
    public float calcDamageByCreature(int level, CreatureAttribute creatureType) {
        return creatureType == CreatureAttribute.ILLAGER ? (float)level * 2.5F : 0.0F;
    }
}