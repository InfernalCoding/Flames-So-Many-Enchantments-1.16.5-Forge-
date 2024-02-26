package dev.infernal_coding.flamesenchants.enchantments.custom;

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

public class ReviledBlade extends Enchantment {
    public ReviledBlade() {
        super(Rarity.VERY_RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 40;
    }

    public static void handleEnchant(LivingDamageEvent fEvent) {
        try {
            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
            ItemStack weapon = Util.getHeldItem(attacker.getActiveHand(), attacker);
            int levelfinish = EnchantmentHelper.getEnchantmentLevel(Registry.REVILED_BLADE.get(), weapon);

            if (levelfinish > 0) {

                float defenderHealthPercent = fEvent.getEntityLiving().getHealth() / fEvent.getEntityLiving().getMaxHealth();
                float dmgMod = (1.0F - defenderHealthPercent) * ((float) levelfinish * 0.1F + 0.9F);
                ++dmgMod;
                float Damage = fEvent.getAmount();
                fEvent.setAmount(dmgMod * Damage);
            }

        } catch (Exception ignored) {}
    }
}