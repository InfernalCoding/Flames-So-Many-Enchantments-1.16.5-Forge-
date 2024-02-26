package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.SoulSpeedEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedSoulSpeed extends SoulSpeedEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedSoulSpeed() {
        super(Rarity.RARE, EquipmentSlotType.FEET);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return (int) (super.getMinEnchantability(p_77321_1_) * 1.5);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return (int) (super.getMaxEnchantability(p_223551_1_) * 1.5);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.SOUL_SPEED && ench != Registry.SUPREME_SOUL_SPEED.get();
    }
}
