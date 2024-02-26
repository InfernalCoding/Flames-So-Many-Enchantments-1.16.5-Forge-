package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SpellBreaker extends Enchantment {
    public SpellBreaker() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
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
        return super.getMinEnchantability(par1) + 30;
    }

    public static void onHurt(LivingHurtEvent e) {
        if (e.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) e.getSource().getTrueSource();
            ItemStack stack = Util.getHeldItem(attacker.getActiveHand(), attacker);
            int level = EnchantmentHelper.getEnchantmentLevel(Registry.SPELL_BREAKER.get(), stack);
            if (level > 0) {
                if (e.getEntityLiving() instanceof WitchEntity || e.getEntityLiving() instanceof EvokerEntity) {
                    e.setAmount(e.getAmount() + 1.5F * (float) level);
                }
            }
        }
    }
}
