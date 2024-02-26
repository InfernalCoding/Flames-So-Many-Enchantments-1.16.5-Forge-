package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import static java.lang.Math.max;

public class AdvancedBaneOfAnthropods extends DamageEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedBaneOfAnthropods(Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, 2, slots);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return super.getMinEnchantability(p_77321_1_) * 2;
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMaxEnchantability(p_223551_1_) * 2;
    }

    @Override
    public float calcDamageByCreature(int level, CreatureAttribute creatureType) {
        return creatureType == CreatureAttribute.ARTHROPOD ?  15 + max(0, level - 1) * 3.75f : 0.0f;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity ) {
            LivingEntity entity = (LivingEntity) target;
            if (entity.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
                int amplifier = 3 + level;
                int duration = (int) (75 + 37.5 * level);
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, duration, amplifier));
            }
        }
    }
}

