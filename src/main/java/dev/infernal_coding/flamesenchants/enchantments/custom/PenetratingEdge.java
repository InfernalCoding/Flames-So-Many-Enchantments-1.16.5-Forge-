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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PenetratingEdge extends Enchantment {
    public PenetratingEdge() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 6;
    }

    public int getMinEnchantability(int par1) {
        return 14 + 12 * (par1 - 1);
    }

    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 40;
    }

    @Override
    public boolean canApplyTogether(Enchantment fTest) {
        return super.canApplyTogether(fTest) && !(fTest instanceof Rune);
    }

    public static void handleEnchant(LivingHurtEvent fEvent) {

        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
            ItemStack weapon = Util.getHeldItem(attacker.getActiveHand(), attacker);
            float armor = fEvent.getEntityLiving().getTotalArmorValue();
            if (armor >= 3.0F) {
                int level = EnchantmentHelper.getEnchantmentLevel(Registry.PENETRATING_EDGE.get(), weapon);
                if (level != 0) {
                    fEvent.setAmount(fEvent.getAmount() + (armor / 3.0F + 0.5F + (float) level * 0.16F));
                }
            }
        }
    }
}
