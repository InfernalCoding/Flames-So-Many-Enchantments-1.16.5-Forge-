package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class RuneArrowPiercing extends Rune {

    public RuneArrowPiercing() {
        super(Rarity.VERY_RARE, EnchantmentType.BOW,
                new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
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
    public int getMinEnchantability(int p_77321_1_) {
        return super.getMinEnchantability(p_77321_1_) + 30;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 25 + (level - 1) * 15;
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST,
            receiveCanceled = true
    )
    public static void onEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ArrowEntity) {
            ArrowEntity arrow = (ArrowEntity) event.getEntity();
            LivingEntity shooter = (LivingEntity) arrow.getShooter();
            if (shooter == null) {
                return;
            }

            ItemStack bow = Util.getHeldItem(shooter.getActiveHand(), shooter);

            int p = EnchantmentHelper.getEnchantmentLevel(Registry.ARROW_PIERCING_RUNE.get(), bow);
            if (p > 0) {
                arrow.setPierceLevel((byte) p);
            }
        }
    }

}
