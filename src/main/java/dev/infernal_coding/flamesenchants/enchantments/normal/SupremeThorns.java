package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.Map;
import java.util.Random;

public class SupremeThorns extends ThornsEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeThorns() {
        super(Rarity.VERY_RARE, Registry.ARMOR_SLOTS);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return super.getMinEnchantability(p_77321_1_) * 2;
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMaxEnchantability(p_223551_1_) * 2;
    }

    @Override
    public void onUserHurt(LivingEntity user, Entity attacker, int level) {
        Random random = user.getRNG();
        Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWithEnchantment(Registry.SUPREME_THORNS.get(), user);
        if (shouldHit(level, random)) {
            attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float) getDamage(level, random));

            if (entry != null) {
                entry.getValue().damageItem(2, user, (livingEntity) -> {
                    livingEntity.sendBreakAnimation(entry.getKey());
                });
            }
        }
    }

    public static boolean shouldHit(int level, Random rnd) {
        return level >= 0;
    }
    public static int getDamage(int level, Random rnd) {
        return level > 10 ? level - 10 : 5 + level * 2 + rnd.nextInt(level * 2 + 4);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.THORNS && ench != Registry.ADVANCED_THORNS.get();
    }
}
