package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ImpalingEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

public class AdvancedImpaling extends ImpalingEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedImpaling() {
        super(Rarity.VERY_RARE, EquipmentSlotType.MAINHAND);
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
        return creatureType == CreatureAttribute.WATER ? 15 + Math.max(0, level - 1) * 3.75f : 0.0F;

    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.IMPALING;
    }
}
