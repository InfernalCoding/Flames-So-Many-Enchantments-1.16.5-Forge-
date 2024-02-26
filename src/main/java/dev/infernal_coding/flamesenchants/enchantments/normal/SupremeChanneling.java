package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.ChannelingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class SupremeChanneling extends ChannelingEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeChanneling(Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, slots);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 100;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.CHANNELING && ench != Registry.ADVANCED_CHANNELING.get();
    }
}
