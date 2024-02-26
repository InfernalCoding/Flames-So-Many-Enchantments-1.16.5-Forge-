package dev.infernal_coding.flamesenchants.enchantments.normal;

import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

public class AdvancedFireProtection extends ProtectionEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedFireProtection(Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, Type.FIRE, slots);
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
        return source.isFireDamage() ? 8 + level * 4 : 0;
    }

}
