package dev.infernal_coding.flamesenchants.enchantments.normal;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;

public class SupremeSharpness extends DamageEnchantment {
    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeSharpness() {
        super(Rarity.VERY_RARE, 0, EquipmentSlotType.MAINHAND);
    }

    @Override
    public int getMinEnchantability(int level) {
        return (int) (super.getMinEnchantability(level) * 2.5);
    }

    @Override
    public int getMaxEnchantability(int level) {
        return (int) (super.getMaxEnchantability(level) * 2.5);
    }

    @Override
    public float calcDamageByCreature(int level, CreatureAttribute p_152376_2_) {
        return 4f + Math.max(0, level - 1) * 1.6f;
    }
}
