package dev.infernal_coding.flamesenchants.enchantments.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.Random;


public class Brutality extends Enchantment {

    static final Random rand = new Random();

    public Brutality() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {

        if (target instanceof LivingEntity) {
            LivingEntity victim = (LivingEntity) target;
            Iterable<ItemStack> iter = victim.getArmorInventoryList();
            int x = 5;

            Iterator var8;
            ItemStack item;
            for (var8 = iter.iterator(); var8.hasNext(); --x) {
                item = (ItemStack) var8.next();
            }

            var8 = iter.iterator();

            while (var8.hasNext()) {
                item = (ItemStack) var8.next();
                int currentDamage = item.getDamage();
                item.setDamage((int) (currentDamage - currentDamage * 0.0025F * (float) x + rand.nextInt(level + 2) + 1));
            }

        }
    }
}