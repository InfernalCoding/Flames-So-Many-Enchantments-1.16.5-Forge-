package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RandomChanceWithLooting.class)
public class RandomChanceWithLootingMixin {

    @Shadow @Final private float chance;

    @Shadow @Final private float lootingMultiplier;

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of looting
     */
    @Overwrite
    public boolean test(LootContext context) {
        int i = context.getLootingModifier();
        Entity entity = context.get(LootParameters.KILLER_ENTITY);

        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;

            int i1 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.SUPREME_LOOTING.get(), living);
            int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_LOOTING.get(), living);

            if (i1 > 0) {
                return context.getRandom().nextFloat() < this.chance + .6 + i1 * 4 * this.lootingMultiplier;
            } else if (i2 > 0) {
                return context.getRandom().nextFloat() < this.chance + .3 + i2 * 2 * this.lootingMultiplier;
            }

        }
        return context.getRandom().nextFloat() < this.chance + (float)i * this.lootingMultiplier;
    }
}
