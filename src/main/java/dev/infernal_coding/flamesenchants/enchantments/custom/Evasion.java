package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Evasion extends Enchantment {
    public Evasion() {
        super(Rarity.RARE, EnchantmentType.ARMOR_LEGS, new EquipmentSlotType[]{EquipmentSlotType.LEGS});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 25 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 40;
    }

    public static void handleEnchant(LivingAttackEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
            if (fEvent.getEntity() instanceof LivingEntity) {
                LivingEntity victim = fEvent.getEntityLiving();
                if (victim != null) {
                    LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
                    int level = Util.getLegsLevel(Registry.EVASION.get(), attacker);

                    if (victim.getRNG().nextInt(100) >= 75) {
                        if (level > 0) {
                            double randX = 0.65D + victim.getRNG().nextDouble() * 0.25D;
                            double randZ = 0.65D + victim.getRNG().nextDouble() * 0.25D;
                            if (fEvent.getEntityLiving().getRNG().nextInt(100) < 5 + level * 15) {
                                fEvent.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}