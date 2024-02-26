package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DarkShadows extends Enchantment {
    public DarkShadows() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 16 + (par1 - 1) * 12;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 40;
    }


    @Override
    public void onEntityDamaged(LivingEntity user, Entity entiti, int level) {
        if (level >= 3 && entiti instanceof LivingEntity) {
            LivingEntity e = (LivingEntity) entiti;
            World world = user.world;

            if (world.getLight(user.getPosition()) <= 0.1F && world.getLight(e.getPosition()) <= 0.1F) {
                e.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 160));
            }

        }

    }

    public static void onEvent(LivingHurtEvent e) {
        Entity a = e.getSource().getTrueSource();

        if (!(a instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity) a;
        ItemStack src = Util.getHeldItem(attacker.getActiveHand(), attacker);

        int level = EnchantmentHelper.getEnchantmentLevel(Registry.DARK_SHADOWS.get(), src);
        if (level > 0) {
            World world = attacker.world;

            if (world.getLight(attacker.getPosition()) <= 0.1F && world.getLight(e.getEntity().getPosition()) <= 0.1F) {
                e.setAmount(e.getAmount() + (float) level * 0.75F);
            }
        }
    }
}
