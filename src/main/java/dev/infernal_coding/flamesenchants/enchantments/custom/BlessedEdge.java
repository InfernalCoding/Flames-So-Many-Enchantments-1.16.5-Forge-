package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BlessedEdge extends Enchantment {
    public BlessedEdge() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof Lifesteal);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 20 + 10 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 40;
    }

    public static void handleEnchant(LivingHurtEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
            if (attacker != null) {
                ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
                int level = EnchantmentHelper.getEnchantmentLevel(Registry.BLESSED_EDGE.get(), dmgSource);

                if (level > 0) {
                    attacker.setHealth(attacker.getHealth() + fEvent.getAmount() * level * 0.03F);
                    if (fEvent.getEntityLiving().getCreatureAttribute() == CreatureAttribute.UNDEAD) {
                        float fDamage = Util.calculateDamageIgnoreSwipe(fEvent.getAmount(), 1.0F, 0.6F, 1.0F + 0.04F * (float) level, attacker, level);
                        fEvent.setAmount(fEvent.getAmount() + fDamage);
                    }
                }
            }
        }
    }
}
