package dev.infernal_coding.flamesenchants.enchantments.normal;

import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class SupremeFireAspect extends FireAspectEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeFireAspect(Rarity rarityIn, EquipmentSlotType... slots) {
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
}
