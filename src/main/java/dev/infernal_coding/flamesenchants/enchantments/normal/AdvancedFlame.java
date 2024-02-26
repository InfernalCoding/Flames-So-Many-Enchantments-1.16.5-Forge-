package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FlameEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedFlame extends FlameEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedFlame(Rarity rarityIn) {
        super(rarityIn, EquipmentSlotType.MAINHAND);
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
        return super.canApplyTogether(ench) && ench != Enchantments.FLAME && ench != Registry.SUPREME_FLAME.get();
    }
}
