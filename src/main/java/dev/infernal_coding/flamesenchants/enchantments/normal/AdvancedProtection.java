package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.util.DamageSource;

public class AdvancedProtection extends ProtectionEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedProtection() {
        super(Rarity.RARE, Type.ALL, Registry.ARMOR_SLOTS);
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
    public int calcModifierDamage(int level, DamageSource source) {
        return 4 + level * 2;
    }

}
