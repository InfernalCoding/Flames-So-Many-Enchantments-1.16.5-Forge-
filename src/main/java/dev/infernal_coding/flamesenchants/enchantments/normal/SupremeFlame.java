package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FlameEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class SupremeFlame extends FlameEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeFlame(Rarity rarityIn) {
        super(rarityIn, EquipmentSlotType.MAINHAND);
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
        return super.canApplyTogether(ench) && ench != Enchantments.FLAME && ench != Registry.ADVANCED_FLAME.get();
    }
}
