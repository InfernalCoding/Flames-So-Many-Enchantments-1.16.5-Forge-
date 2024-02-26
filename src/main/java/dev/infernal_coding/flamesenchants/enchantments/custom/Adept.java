package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.LootBonusEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class Adept extends Enchantment {
    public Adept() {
        super(Rarity.RARE, EnchantmentType.WEAPON,
                new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof LootBonusEnchantment) && !(ench instanceof SubjectEnchant);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantability(int level) {
        return super.getMinEnchantability(level) + 40;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 26 + (level - 1) * 12;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @SubscribeEvent
    public static void onDeath(LivingExperienceDropEvent event) {
        PlayerEntity player = event.getAttackingPlayer();

        if (player != null) {
            ItemStack stack = Util.getHeldItem(player.getActiveHand(), player);
            int i = EnchantmentHelper.getEnchantmentLevel(Registry.ADEPT.get(), stack);

            if (i > 0 && event.getOriginalExperience() > 0
                    && event.getEntityLiving() != null) {
                event.setDroppedExperience((int) (2 + i + (event.getOriginalExperience() * (.75f + .5f * i))));
            }
        }
    }

}

