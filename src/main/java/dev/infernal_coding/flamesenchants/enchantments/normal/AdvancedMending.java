package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedMending extends Enchantment {

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedMending() {
        super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, EquipmentSlotType.values());
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return (int) (1.5 * p_77321_1_ * 25);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return 2 * this.getMinEnchantability(p_223551_1_) + 50;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.MENDING;
    }
}
