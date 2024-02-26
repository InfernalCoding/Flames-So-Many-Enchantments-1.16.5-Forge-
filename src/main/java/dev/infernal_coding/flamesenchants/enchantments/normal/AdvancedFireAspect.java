package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedFireAspect extends FireAspectEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedFireAspect(Rarity rarityIn, EquipmentSlotType... slots) {
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
        return super.canApplyTogether(ench) && ench != Enchantments.FIRE_ASPECT && ench != Registry.SUPREME_FIRE_ASPECT.get();
    }
}
