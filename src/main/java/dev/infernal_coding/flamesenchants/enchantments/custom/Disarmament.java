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
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Disarmament extends Enchantment {
    public Disarmament() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }



    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 10 + 20 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }

    public static void handleEnchant(LivingHurtEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
                LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
                if (attacker != null) {
                    ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
                    int leveldisarm = EnchantmentHelper.getEnchantmentLevel(Registry.DISARMAMENT.get(), dmgSource);

                    if (leveldisarm > 0) {
                        if (Math.random() * 100.0D < 25.0D) {
                            fEvent.getEntityLiving().addPotionEffect(new EffectInstance(Effects.NAUSEA, 20, 1));
                            fEvent.getEntityLiving().addPotionEffect(new EffectInstance(Effects.SLOWNESS, 40 + leveldisarm * 20, 254));
                        }
                    }
                }
        }
    }
}
