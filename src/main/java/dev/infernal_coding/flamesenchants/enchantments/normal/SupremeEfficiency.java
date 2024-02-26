package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class SupremeEfficiency extends EfficiencyEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeEfficiency(Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, slots);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return (super.getMinEnchantability(enchantmentLevel) * 2);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return (super.getMaxEnchantability(enchantmentLevel) * 2);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.EFFICIENCY && ench != Registry.ADVANCED_EFFICIENCY.get();
    }
}
