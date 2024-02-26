package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ProtectionEnchantment.class)
public class ProtectionEnchantmentMixin {

    /**
     * @author Infernal_Coding
     * @reason Modifies the knockback from an explosion
     */
    @Overwrite
    public static double getBlastDamageReduction(LivingEntity entityLivingBaseIn, double damage) {

        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.BLAST_PROTECTION, entityLivingBaseIn);
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_BLAST_PROTECTION.get(), entityLivingBaseIn);

        if (i2 > 0) {
            damage -= MathHelper.floor(damage * (double)((float) i2 * 0.3F));
        } else if (i > 0) {
            damage -= MathHelper.floor(damage * (double)((float)i * 0.15F));
        }

        return damage;
    }

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of fire resistance
     */
    @Overwrite
    public static int getFireTimeForEntity(LivingEntity livingEntity, int level) {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FIRE_PROTECTION, livingEntity);
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_FIRE_PROTECTION.get(), livingEntity);

        if (i2 > 0) {
            level -= MathHelper.floor(level * .6F + level * i * 0.05F);
        } else if (i > 0) {
            level -= MathHelper.floor((float)level * (float)i * 0.15F);
        }

        return level;
    }

}
