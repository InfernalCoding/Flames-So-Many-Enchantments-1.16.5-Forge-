package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel;
import static net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of sweeping edge
     */
    @Overwrite
    public static float getSweepingDamageRatio(LivingEntity entityIn) {
        int i = getMaxEnchantmentLevel(Enchantments.SWEEPING, entityIn);
        int i2 = getMaxEnchantmentLevel(Registry.ADVANCED_SWEEPING.get(), entityIn);
        int i3 = getMaxEnchantmentLevel(Registry.SUPREME_SWEEPING.get(), entityIn);

        if (i3 > 0 || i2 > 0) {
            return 1.0f;
        } else return i > 0 ? SweepingEnchantment.getSweepingDamageRatio(i) : 0.0F;
    }

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of fire aspect
     */
    @Overwrite
    public static int getFireAspectModifier(LivingEntity player) {
        int level1 = getMaxEnchantmentLevel(Registry.SUPREME_FIRE_ASPECT.get(), player);
        int level2 = getMaxEnchantmentLevel(Registry.ADVANCED_FIRE_ASPECT.get(), player);

        if (level1 > 0) {
            return level1;
        } else if (level2 > 0) return level2;

        return getMaxEnchantmentLevel(Enchantments.FIRE_ASPECT, player);
    }

    /**
     * @author Infernal_Coding
     * @reason Greater compat for high-tier frost walker
     */
    @Overwrite
    public static boolean hasFrostWalker(LivingEntity player) {
        return getMaxEnchantmentLevel(Enchantments.FROST_WALKER, player) > 0 ||
                getMaxEnchantmentLevel(Registry.SUPREME_FROST_WALKER.get(), player) > 0 ||
                getMaxEnchantmentLevel(Registry.ADVANCED_FROST_WALKER.get(), player) > 0;
    }

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of luck of the sea
     */
    @Overwrite
    public static int getFishingLuckBonus(ItemStack stack) {
        int i1 = getEnchantmentLevel(Registry.SUPREME_LUCK_OF_THE_SEA.get(), stack);
        int i2 = getEnchantmentLevel(Registry.ADVANCED_LUCK_OF_THE_SEA.get(), stack);

        if (i1 > 0) {
            return 9 + i1 * 4;
        } else if (i2 > 0) {
            return 3 + i2 * 2;
        } else return getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, stack);
    }

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of lure
     */
    @Overwrite
    public static int getFishingSpeedBonus(ItemStack stack) {
        int i1 = getEnchantmentLevel(Registry.SUPREME_LURE.get(), stack);
        int i2 = getEnchantmentLevel(Registry.ADVANCED_LURE.get(), stack);

        if (i1 > 0) {
            return 2 + i1;
        } else if (i2 > 0) {
            return 1 + i2;
        } else return getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, stack);
    }

    @Redirect(method = "getEnchantmentLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/Registry;getKey(Ljava/lang/Object;)Lnet/minecraft/util/ResourceLocation;"))
    private static<T> ResourceLocation getEnchantmentKey(net.minecraft.util.registry.Registry<T> instance, T t) {
        Enchantment enchantment = (Enchantment) t;
        return ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
    }
}
