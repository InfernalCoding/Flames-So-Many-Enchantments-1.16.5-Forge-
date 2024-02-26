package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

public class AdvancedProjectileProtection extends ProtectionEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedProjectileProtection() {
        super(Rarity.RARE, Type.PROJECTILE, Registry.ARMOR_SLOTS);
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
        return source.isProjectile() ? 8 + level * 4 : 0;
    }

}
