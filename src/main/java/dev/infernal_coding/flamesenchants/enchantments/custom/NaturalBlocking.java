package dev.infernal_coding.flamesenchants.enchantments.custom;

import com.mojang.authlib.yggdrasil.YggdrasilGameProfileRepository;
import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class NaturalBlocking extends Enchantment {
    public NaturalBlocking() {
        super(Rarity.RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
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
        return 18 + 12 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 40;
    }

    public static void naturalBlock(LivingDamageEvent fEvent) {
        LivingEntity victim = fEvent.getEntityLiving();
        if (victim != null) {
            ItemStack shield1 = victim.getHeldItemMainhand();
            ItemStack shield2 = victim.getHeldItemOffhand();
            if (shield1.isShield(victim) && EnchantmentHelper.getEnchantmentLevel(Registry.NATURAL_BLOCKING.get(), shield1) > 0) {
                int level = EnchantmentHelper.getEnchantmentLevel(Registry.NATURAL_BLOCKING.get(), shield1);

                if (level > 0) {
                    float damage = fEvent.getAmount() - fEvent.getAmount() * ((float) level * 0.1F + 0.1F);
                    float percentage = damage / fEvent.getAmount();
                    percentage = 1.0F - percentage;
                    fEvent.setAmount(damage);
                    if (level >= 3) {
                        shield1.setDamage(shield1.getDamage() - (int) (1.25F * fEvent.getAmount() * percentage) + 1);
                    } else {
                        shield1.setDamage(shield1.getDamage() - (int) (1.75F * fEvent.getAmount() * percentage) + 1);
                    }
                }
            } else if (shield2.isShield(victim)) {
                int level = EnchantmentHelper.getEnchantmentLevel(Registry.NATURAL_BLOCKING.get(), shield2);

                if (level > 0) {
                    float damage = fEvent.getAmount() - fEvent.getAmount() * ((float) level * 0.1F + 0.1F);
                    float percentage = damage / fEvent.getAmount();
                    percentage = 1.0F - percentage;
                    fEvent.setAmount(damage);
                    if (level >= 3) {
                        shield2.setDamage(shield2.getDamage() - (int) (1.25F * fEvent.getAmount() * percentage) + 1);
                    } else {
                        shield2.setDamage(shield2.getDamage() - (int) (1.75F * fEvent.getAmount() * percentage) + 1);
                    }
                }
            }
        }
    }
}