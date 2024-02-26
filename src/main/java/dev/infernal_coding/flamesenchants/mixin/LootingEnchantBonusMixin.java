package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.LootingEnchantBonus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LootingEnchantBonus.class)
public abstract class LootingEnchantBonusMixin {

    @Shadow @Final private RandomValueRange count;

    @Shadow protected abstract boolean func_215917_b();

    @Shadow @Final private int limit;


    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of looting
     */
    @Overwrite
    public ItemStack doApply(ItemStack stack, LootContext context) {
        Entity entity = context.get(LootParameters.KILLER_ENTITY);
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            int i = context.getLootingModifier();
            int i1 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.SUPREME_LOOTING.get(), living);
            int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_LOOTING.get(), living);
            float f = 0.0f;

            if (i1 > 0) {
                f = (float) .6 + i * 4 * this.count.generateFloat(context.getRandom());
            } else if (i2 > 0) {
                f = (float) .3 + i * 2 * this.count.generateFloat(context.getRandom());
            } else if (i > 0) {
                f = (float)i * this.count.generateFloat(context.getRandom());
            }

            stack.grow(Math.round(f));
            if (this.func_215917_b() && stack.getCount() > this.limit) {
                stack.setCount(this.limit);
            }
        }
        return stack;
    }

}
