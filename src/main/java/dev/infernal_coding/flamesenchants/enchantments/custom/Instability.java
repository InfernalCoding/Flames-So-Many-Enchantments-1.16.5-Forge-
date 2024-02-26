package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Instability extends Enchantment {
    public Instability() {
        super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 30 + (par1 - 1) * 15;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 30;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }


    public static void onAttack(LivingDamageEvent e) {
        if (!e.isCanceled() && e.getAmount() > 0.0F && e.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity attacker = (PlayerEntity) e.getSource().getTrueSource();

            ItemStack stack = Util.getHeldItem(attacker.getActiveHand(), attacker);
            int level = EnchantmentHelper.getEnchantmentLevel(Registry.INSTABILITY.get(), stack);
            if (level <= 0) return;

            float percentage = (float)stack.getDamage() / (float)stack.getMaxDamage();
            percentage = 1.0F + percentage * 0.75F * (float)level;
            e.setAmount(e.getAmount() * percentage);
            stack.setDamage((int) (stack.getDamage() + e.getAmount()));
        }

    }
}
