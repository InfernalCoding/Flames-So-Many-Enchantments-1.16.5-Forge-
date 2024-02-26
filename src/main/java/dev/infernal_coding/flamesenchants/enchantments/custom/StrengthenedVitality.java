package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class StrengthenedVitality extends Enchantment {

    public StrengthenedVitality() {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_CHEST, new EquipmentSlotType[]{EquipmentSlotType.CHEST});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 25 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 75;
    }

    @SubscribeEvent
    public static void armorChange(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlotType.CHEST) {
            LivingEntity entity = event.getEntityLiving();

            ItemStack from = event.getFrom();
            ItemStack to = event.getTo();

            int levelFrom = EnchantmentHelper.getEnchantmentLevel(Registry.STRENGTHENED_VITALITY.get(), from);
            int levelTo = EnchantmentHelper.getEnchantmentLevel(Registry.STRENGTHENED_VITALITY.get(), to);

            if (levelFrom > 0) {
                float health = entity.getMaxHealth() / (1 + levelFrom * .1f);
                entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            }

            if (levelTo > 0) {
                float health = entity.getMaxHealth() + (entity.getMaxHealth() * levelTo * .1f);
                entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            }
        }
    }
}
