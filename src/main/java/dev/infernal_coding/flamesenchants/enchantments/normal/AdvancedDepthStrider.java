package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedDepthStrider extends DepthStriderEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedDepthStrider(Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, slots);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return (int) (super.getMinEnchantability(enchantmentLevel) * 1.5);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return (int) (super.getMaxEnchantability(enchantmentLevel) * 1.5);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.DEPTH_STRIDER && ench != Registry.SUPREME_DEPTH_STRIDER.get();
    }
}
