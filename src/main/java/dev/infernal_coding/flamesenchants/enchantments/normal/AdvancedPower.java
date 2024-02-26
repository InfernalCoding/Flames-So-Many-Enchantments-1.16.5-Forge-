package dev.infernal_coding.flamesenchants.enchantments.normal;

import net.minecraft.enchantment.PowerEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedPower extends PowerEnchantment {
    public AdvancedPower() {
        super(Rarity.RARE, EquipmentSlotType.MAINHAND);
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return (int) (super.getMinEnchantability(p_77321_1_) * 1.5);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return (int) (super.getMaxEnchantability(p_223551_1_) * 1.5);
    }
}
