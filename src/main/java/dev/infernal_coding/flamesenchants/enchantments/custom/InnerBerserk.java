package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class InnerBerserk extends Enchantment {
    public InnerBerserk() {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_CHEST, new EquipmentSlotType[]{EquipmentSlotType.CHEST});
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
        Entity a = fEvent.getSource().getTrueSource();

        if (!(a instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity) a;

        int levelfinish = Util.getLegsLevel(Registry.INNER_BERSERK.get(), attacker);

        if (levelfinish > 0) {
            float defenderHealthPercent = attacker.getHealth() / attacker.getMaxHealth();
            float dmgMod = (1.0F - defenderHealthPercent) * ((float) levelfinish * 0.05F + 1.1F);
            ++dmgMod;
            float Damage = fEvent.getAmount();
            fEvent.setAmount(dmgMod * Damage);
        }
    }
}
