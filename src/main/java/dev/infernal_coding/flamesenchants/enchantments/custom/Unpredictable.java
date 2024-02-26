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

public class Unpredictable extends Enchantment {
    public Unpredictable() {
        super(Rarity.VERY_RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 20 + 10 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 40;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    public static void handleEnchant(LivingHurtEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
            if (attacker != null) {
                ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
                int levelDamageRandomizer = EnchantmentHelper.getEnchantmentLevel(Registry.UNPREDICTABLE.get(), dmgSource);

                if (levelDamageRandomizer > 0) {
                    float random = (float) Math.random() * fEvent.getAmount() * (float) levelDamageRandomizer * 1.25F;
                    random = (float) (Math.floor((double) random) + 1.0D);
                    fEvent.setAmount(random + 2.0F);
                    if (fEvent.getEntityLiving().getRNG().nextInt(100) <= 45) {
                        fEvent.setAmount(0.0F);
                        fEvent.getEntityLiving().setHealth(fEvent.getEntityLiving().getHealth() + random);
                    }
                }
            }
        }
    }
}
