package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CombatRules;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class WaterAspect extends Enchantment {
    public WaterAspect() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 5 + 10 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 30;
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof DamageEnchantment);
    }

    public static void handleEnchant(LivingHurtEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
            LivingEntity victim = fEvent.getEntityLiving();

            if (attacker != null) {
                ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
                int levelWaterAspect = EnchantmentHelper.getEnchantmentLevel(Registry.WATER_ASPECT.get(), dmgSource);

                if (levelWaterAspect > 0) {
                    boolean affected = victim instanceof BlazeEntity
                            || victim instanceof EndermanEntity
                            || victim instanceof MagmaCubeEntity;

                    if (fEvent.getEntityLiving().isInWater() || affected) {
                        float damage = 2.5f * levelWaterAspect;
                        damage = CombatRules.getDamageAfterAbsorb(damage, victim.getArmorCoverPercentage(), (float) victim.getAttribute(Attributes.ARMOR_TOUGHNESS).getBaseValue());
                        fEvent.setAmount(fEvent.getAmount() + damage);
                    }

                }
            }
        }
    }
}
