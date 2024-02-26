package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.RespirationEnchantment;

public class SupremeRespiration extends RespirationEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeRespiration() {
        super(Rarity.VERY_RARE, Registry.ARMOR_SLOTS);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return super.getMinEnchantability(p_77321_1_) * 2;
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMaxEnchantability(p_223551_1_) * 2;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.RESPIRATION && ench != Registry.ADVANCED_RESPIRATION.get();
    }
}
