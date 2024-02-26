package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CursedEdge extends Enchantment {
    public CursedEdge() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }



    @Override
    public int getMaxLevel() {
        return 5;
    }

    public int getMinEnchantability(int par1) {
        return 20 + 12 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 40;
    }



    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    public static void handleEnchant(LivingDamageEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
            if (attacker != null) {
                ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
                int levelCurse = EnchantmentHelper.getEnchantmentLevel(Registry.CURSED_EDGE.get(), dmgSource);

                if (levelCurse > 0) {
                    float damage = fEvent.getAmount() * ((float) levelCurse * 0.2F + 1.0F);
                    fEvent.setAmount(damage);
                    attacker.attackEntityFrom(DamageSource.MAGIC, damage * 0.25F + 1.0F);
                }
            }
        }
    }
}
