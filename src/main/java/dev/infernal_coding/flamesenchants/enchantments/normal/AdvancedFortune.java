package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.LootBonusEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedFortune extends LootBonusEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedFortune(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType... slots) {
        super(rarityIn, typeIn, slots);
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
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != Registry.SUPREME_FORTUNE.get() && p_77326_1_ != Enchantments.FORTUNE;
    }
}
