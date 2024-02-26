package dev.infernal_coding.flamesenchants.enchantments.custom;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;

public class SubjectEnchant extends DamageEnchantment {
    private static final int[] MIN_COST = new int[]{8, 9, 7, 11, 5, 6};
    private static final int[] LEVEL_COST = new int[]{13, 14, 10, 15, 9, 10};
    private static final int[] LEVEL_COST_SPAN = new int[]{25, 28, 23, 30, 21, 20};

    public SubjectEnchant(int damageTypeIn) {
        super(Rarity.RARE, damageTypeIn, EquipmentSlotType.MAINHAND);
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return damageType;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return MIN_COST[this.damageType] + (enchantmentLevel - 1) * LEVEL_COST[this.damageType];
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + LEVEL_COST_SPAN[this.damageType];
    }

    @Override
    public float calcDamageByCreature(int level, CreatureAttribute creatureType) {
        return this.damageType == 5 ? 0.75F + (float) level * 0.25F : 0.0F;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof SubjectEnchant);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canApply(stack);
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            LivingEntity entitylivingbase = (LivingEntity) target;
            boolean isSmart = target instanceof IronGolemEntity
                    || target instanceof WolfEntity
                    || target instanceof VillagerEntity
                    || target instanceof SnowGolemEntity
                    || target instanceof WitchEntity
                    || target instanceof ZombifiedPiglinEntity
                    || target instanceof PiglinEntity;

            if (this.damageType == 5 && user.getRNG().nextDouble() * 100.0D < 8.5D) {
                if (level == 1 || level == 2) {
                    user.addPotionEffect(new EffectInstance(Effects.HASTE, 150 + level * 30, level - 1));
                    user.addPotionEffect(new EffectInstance(Effects.SPEED, 50 + level * 5, level - 1));
                }

                if (level == 3 || level == 4) {
                    user.addPotionEffect(new EffectInstance(Effects.REGENERATION, 10 + level * 5, level - 4));
                    user.addPotionEffect(new EffectInstance(Effects.SPEED, 150 + level * 30, level - 2));
                    user.addPotionEffect(new EffectInstance(Effects.HASTE, 50 + level * 5, level - 2));
                    user.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 20 + level * 5, level - 3));
                }

                if (level >= 5) {
                    user.addPotionEffect(new EffectInstance(Effects.REGENERATION, 10 + level * 5, level - 1));
                    user.addPotionEffect(new EffectInstance(Effects.SPEED, 20 + level * 5, level - 2));
                    user.addPotionEffect(new EffectInstance(Effects.HASTE, 10 + level * 5, level - 4));
                    user.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 150 + level * 5, level - 2));
                    user.addPotionEffect(new EffectInstance(Effects.STRENGTH, 50 + level * 5, level - 2));
                    user.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 20 + level * 5, level - 3));
                }
            } else if (this.damageType == 4 && isSmart)  {
                MobEntity mob = (MobEntity) target;
                float baseDamage = (float) ((.8 + .3 * level) + 3 * level);

                float finalDamage = CombatRules.getDamageAfterAbsorb(baseDamage,
                        mob.getArmorCoverPercentage(),
                        (float) mob.getAttribute(Attributes.ARMOR_TOUGHNESS).getBaseValue());
                mob.setHealth(mob.getHealth() - finalDamage);
            }
        }

    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }
}
