package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class SupremeDepthStrider extends DepthStriderEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeDepthStrider(Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, slots);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) * 2;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMaxEnchantability(enchantmentLevel) * 2;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.DEPTH_STRIDER && ench != Registry.ADVANCED_DEPTH_STRIDER.get();
    }
}
