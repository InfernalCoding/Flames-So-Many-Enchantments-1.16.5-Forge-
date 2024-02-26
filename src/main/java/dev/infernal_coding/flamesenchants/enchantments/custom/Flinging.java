package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class Flinging extends Enchantment {
    public Flinging() {
        super(Rarity.UNCOMMON, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 5 + 10 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMaxEnchantability(par1) + 20;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        ItemStack stack = Util.getHeldItem(user.getActiveHand(), user);
        int levelknockBack = EnchantmentHelper.getEnchantmentLevel(Registry.FLINGING.get(), stack);
        double y =  levelknockBack * 0.1875D + 0.07500000298023224D;
        BlockPos pos = target.getPosition().add(0, y, 0);
        target.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
    }
}
