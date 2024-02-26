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

public class RainsBestowment extends Enchantment {
    public RainsBestowment() {
        super(Rarity.VERY_RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public int getMaxLevel() {
        return 6;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15 + (par1 - 1) * 15;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 30;
    }

    public boolean canApplyTogether(Enchantment fTest) {
        return super.canApplyTogether(fTest) && !(fTest instanceof ClearskiesFavor);
    }

    public static void handleEvent(LivingHurtEvent fEvent) {
        float skyDamage = 0.0F;
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();

            if (attacker != null) {
                ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
                int level = EnchantmentHelper.getEnchantmentLevel(Registry.RAINS_BESTOWMENT.get(), dmgSource);

                if (level > 0) {
                    float fi;
                    if (isRainy(attacker)) {
                        fi = Util.calculateDamageIgnoreSwipe(fEvent.getAmount(), 0.5F, 0.75F, 1.0F, attacker, level);
                        fEvent.setAmount(fEvent.getAmount() + fi);
                    } else {
                        fi = Util.calculateDamageForNegativeSwipe(fEvent.getAmount(), 0.0F, -0.6F, 1.0F, attacker, level);
                        fEvent.setAmount(fEvent.getAmount() - fi);
                    }
                }
            }
        }
    }

    static boolean isRainy(LivingEntity entity) {
        return !(entity.world.getLight(entity.getPosition()) > 5
                && !entity.world.isRaining()
                && !entity.world.isThundering());
    }
}
