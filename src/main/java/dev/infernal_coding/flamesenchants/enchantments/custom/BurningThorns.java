package dev.infernal_coding.flamesenchants.enchantments.custom;


import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.Map;
import java.util.Random;

public class BurningThorns extends ThornsEnchantment {
    public BurningThorns() {
        super(Rarity.VERY_RARE, EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET);
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 12 + (par1 - 1) * 9;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 30;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench)
                && ench != Registry.SUPREME_THORNS.get()
                && ench != Registry.ADVANCED_THORNS.get()
                && ench != Enchantments.THORNS;
    }

    @Override
    public void onUserHurt(LivingEntity user, Entity attacker, int level) {
        Random random = user.getRNG();
        Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWithEnchantment(Registry.BURNING_THORNS.get(), user);
        if (shouldHit(level, random)) {
            attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float) getDamage(level, random));
            attacker.setFire(random.nextInt(level + 1));

            if (entry != null) {
                entry.getValue().damageItem(2, user, (livingEntity) -> {
                    livingEntity.sendBreakAnimation(entry.getKey());
                });
            }
        }

    }
}
