package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.LootBonusEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AshDestroyer extends Enchantment {

    public AshDestroyer() {
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

    public int getMinEnchantability(int par1) {
        return 14 + (par1 - 1) * 12;
    }

    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 30;
    }



    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench)
                && !(ench instanceof CriticalStrike)
                && !(ench instanceof ReviledBlade);
    }

    public static void handleEnchant(LivingDamageEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity && fEvent.getEntityLiving().isBurning()) {
            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
            ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
            int levelAshBringer = EnchantmentHelper.getEnchantmentLevel(Registry.ASH_DESTROYER.get(), dmgSource);

            if (levelAshBringer > 0) {
                float damage = Util.calculateDamageIgnoreSwipe(fEvent.getAmount(), 0.0F, 0.0F, 1.0F + (float) levelAshBringer * 0.2F, attacker, levelAshBringer);
                fEvent.setAmount(fEvent.getAmount() + damage);
            }
        }
    }

}
