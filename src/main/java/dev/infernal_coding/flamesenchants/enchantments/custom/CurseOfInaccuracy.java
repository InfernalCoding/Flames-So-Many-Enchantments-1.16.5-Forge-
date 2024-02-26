package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class CurseOfInaccuracy extends Enchantment {
    public CurseOfInaccuracy() {
        super(Rarity.VERY_RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int ench) {
        return 15 + (ench - 1) * 15;
    }

    @Override
    public int getMaxEnchantability(int ench) {
        return this.getMinEnchantability(ench) + 30;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }



    @Override
    public boolean isCurse() {
        return true;
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onAttack(LivingAttackEvent e) {
        Entity eb = e.getSource().getTrueSource();

        if (eb instanceof LivingEntity) {

            int level = EnchantmentHelper.getMaxEnchantmentLevel(Registry.CURSE_OF_INACCURACY.get(), (LivingEntity) eb);
            if (level > 0) {
                if (((LivingEntity) eb).getRNG().nextInt(10) < level * 2) {
                    e.setCanceled(true);
                }
            }
        }
    }

}
