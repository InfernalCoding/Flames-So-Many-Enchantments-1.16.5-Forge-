package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class Powerless extends Enchantment {
    public Powerless() {
        super(Enchantment.Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 12 + (par1 - 1) * 12;
    }

    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 30;
    }


    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }
    @SubscribeEvent(
            priority = EventPriority.HIGHEST,
            receiveCanceled = true
    )
    public static void onEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AbstractArrowEntity) {
            AbstractArrowEntity arrow = (AbstractArrowEntity) event.getEntity();
            LivingEntity shooter = (LivingEntity) arrow.getShooter();
            if (shooter == null) {
                return;
            }

            ItemStack bow = shooter.getActiveItemStack();


            int p = EnchantmentHelper.getEnchantmentLevel(Registry.POWERLESS.get(), bow);
            if (p > 0) {
                double ArrowDamage = arrow.getDamage();
                arrow.setDamage(ArrowDamage - (0.5D + (double)p * 0.5D));
            }
        }
    }
}
