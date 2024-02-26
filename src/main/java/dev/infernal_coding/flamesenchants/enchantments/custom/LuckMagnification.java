package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class LuckMagnification extends Enchantment {
    public LuckMagnification() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15 + 15 * (par1 - 1);
    }

    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 30;
    }

    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void onCritical(CriticalHitEvent e) {
        if (e.getPlayer() != null) {
            if (e.getTarget() != null) {
                ModifiableAttributeInstance luck = e.getPlayer().getAttribute(Attributes.LUCK);
                float amount = (float)luck.getBaseValue();
                if (amount != 0.0F) {
                    int level = EnchantmentHelper.getMaxEnchantmentLevel(Registry.LUCK_MAGNIFICATION.get(), e.getPlayer());
                    if (level > 0) {
                        if ((float) e.getPlayer().getRNG().nextInt(100) < Math.abs(amount * (float) level)) {
                            e.setDamageModifier(e.getDamageModifier() + amount * (float) level * 0.05F);
                        }

                    }
                }
            }
        }
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void handleEnchant(LootingLevelEvent fEvent) {
        if (fEvent.getDamageSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) fEvent.getDamageSource().getTrueSource();
            ItemStack sword = player.getHeldItem(player.getActiveHand());
            int level = EnchantmentHelper.getEnchantmentLevel(Registry.LUCK_MAGNIFICATION.get(), sword);
            if (level > 0) {
                ModifiableAttributeInstance luck = player.getAttribute(Attributes.LUCK);
                int modifier = (int)((double)fEvent.getLootingLevel() + luck.getBaseValue() * (double)level / 2.0D);
                fEvent.setLootingLevel(modifier);
            }
        }
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void magnifyLuck(TickEvent.PlayerTickEvent e) {
        if (e.player != null) {
            if (e.phase != TickEvent.Phase.START) {
                int level = EnchantmentHelper.getEnchantmentLevel(Registry.LUCK_MAGNIFICATION.get(), Util.getHeldItem(e.player.getActiveHand(), e.player));
                if (level > 0) {
                    e.player.addPotionEffect(new EffectInstance(Effects.LUCK, 10, level - 1, true, false));
                }
            }
        }
    }
}
