package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of quick charge
     */
    @Overwrite
    public static int getChargeTime(ItemStack stack) {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int i2 = EnchantmentHelper.getEnchantmentLevel(Registry.ADVANCED_QUICK_CHARGE.get(), stack);
        int i3 = EnchantmentHelper.getEnchantmentLevel(Registry.SUPREME_QUICK_CHARGE.get(), stack);

        if (i3 > 0) {
            return 9 - 3 * i3;
        } else if (i2 > 0) {
            return 15 - 3 * i2;
        } else return i == 0 ? 25 : 25 - 5 * i;
    }

    @Redirect(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I"))
    private int getQuickCharge(Enchantment enchantment, ItemStack stack) {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int i2 = EnchantmentHelper.getEnchantmentLevel(Registry.ADVANCED_QUICK_CHARGE.get(), stack);
        int i3 = EnchantmentHelper.getEnchantmentLevel(Registry.SUPREME_QUICK_CHARGE.get(), stack);

        if (i3 > 0) {
            return i3;
        } else if (i2 > 0) {
            return i2;
        } else return i;
    }

    @Redirect(method = "createArrow", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I"))
    private static int $setPiercing(Enchantment p_77506_0_, ItemStack crossbow) {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, crossbow);
        int i2 = EnchantmentHelper.getEnchantmentLevel(Registry.ADVANCED_PIERCING.get(), crossbow);
        int i3 = EnchantmentHelper.getEnchantmentLevel(Registry.SUPREME_PIERCING.get(), crossbow) ;

        if (i3 > 0) {
            return 12 + i3 * 4;
        } else if (i2 > 0) {
            return 5 + i2 * 2;
        }
        return i;
    }


    /**
     * @author Infernal_Coding
     * @reason To make higher tier multishot
     */
    @Overwrite
    private static boolean hasAmmo(LivingEntity entityIn, ItemStack stack) {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, stack);
        int i2 = EnchantmentHelper.getEnchantmentLevel(Registry.ADVANCED_MULTISHOT.get(), stack);
        int i3 = EnchantmentHelper.getEnchantmentLevel(Registry.SUPREME_MULTISHOT.get(), stack);
        int j = i == 0 ? 1 : 3;

        if (i3 > 0) {
            j = 7;
        } else if (i2 > 0) {
            j = 5;
        }

        boolean flag = entityIn instanceof PlayerEntity && ((PlayerEntity)entityIn).abilities.isCreativeMode;
        ItemStack itemstack = entityIn.findAmmo(stack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!func_220023_a(entityIn, stack, itemstack, k > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    @Unique
    private static boolean func_220023_a(LivingEntity p_220023_0_, ItemStack stack, ItemStack p_220023_2_, boolean p_220023_3_, boolean p_220023_4_) {
        if (p_220023_2_.isEmpty()) {
            return false;
        } else {
            boolean flag = p_220023_4_ && p_220023_2_.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !p_220023_4_ && !p_220023_3_) {
                itemstack = p_220023_2_.split(1);
                if (p_220023_2_.isEmpty() && p_220023_0_ instanceof PlayerEntity) {
                    ((PlayerEntity)p_220023_0_).inventory.deleteStack(p_220023_2_);
                }
            } else {
                itemstack = p_220023_2_.copy();
            }

            addChargedProjectile(stack, itemstack);
            return true;
        }
    }

    @Unique
    private static void addChargedProjectile(ItemStack crossbow, ItemStack projectile) {
        CompoundNBT compoundnbt = crossbow.getOrCreateTag();
        ListNBT listnbt;
        if (compoundnbt.contains("ChargedProjectiles", 9)) {
            listnbt = compoundnbt.getList("ChargedProjectiles", 10);
        } else {
            listnbt = new ListNBT();
        }

        CompoundNBT compoundnbt1 = new CompoundNBT();
        projectile.write(compoundnbt1);
        listnbt.add(compoundnbt1);
        compoundnbt.put("ChargedProjectiles", listnbt);
    }
}
