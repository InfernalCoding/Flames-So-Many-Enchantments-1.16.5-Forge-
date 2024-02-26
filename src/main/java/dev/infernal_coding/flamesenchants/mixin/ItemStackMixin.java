package dev.infernal_coding.flamesenchants.mixin;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract boolean isDamageable();

    @Shadow public abstract int getDamage();

    @Shadow public abstract void setDamage(int damage);

    @Shadow public abstract int getMaxDamage();

    @Shadow public abstract Item getItem();

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of unbreaking
     */
    @Overwrite
    public boolean attemptDamageItem(int amount, Random rand, @Nullable ServerPlayerEntity damager) {
        ItemStack stack = (ItemStack) (Object) this;

        if (!this.isDamageable()) {
            return false;
        } else {
            if (amount > 0) {
                int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
                int i2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
                int i3 = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
                int j = 0;

                if (i3 > 0) {
                    for (int k = 0; k < amount; ++k) {
                        if (negateSupremeDamage(i3, rand)) {
                            ++j;
                        }
                    }
                } else if (i2 > 0) {
                    for (int k = 0; k < amount; ++k) {
                        if (negateAdvancedDamage(i2, rand)) {
                            ++j;
                        }
                    }
                } else if (i > 0) {
                    for (int k = 0; k < amount; ++k) {
                        if (UnbreakingEnchantment.negateDamage(stack, i, rand)) {
                            ++j;
                        }
                    }
                }

                amount -= j;
                if (amount <= 0) {
                    return false;
                }
            }

            if (damager != null && amount != 0) {
                CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(damager, stack, this.getDamage() + amount);
            }

            int l = this.getDamage() + amount;
            this.setDamage(l);
            return l >= this.getMaxDamage();
        }
    }

    @Unique
    private boolean negateAdvancedDamage(int level, Random rand) {
        return rand.nextDouble() * (level + 1) > 1 - 1.0 / (level * 3 + 5);
    }

    @Unique
    private boolean negateSupremeDamage(int level, Random rand) {
        return rand.nextDouble() * (level + 1) > 1 - 1.0 / (level * 6 + 11);
    }

}
