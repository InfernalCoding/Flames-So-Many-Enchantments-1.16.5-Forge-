package dev.infernal_coding.flamesenchants.enchantments.normal;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;

public class SupremeSmite extends DamageEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public SupremeSmite() {
        super(Rarity.VERY_RARE, 1, EquipmentSlotType.MAINHAND);
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
    public float calcDamageByCreature(int level, CreatureAttribute attribute) {
        return attribute == CreatureAttribute.UNDEAD ? 25 + Math.max(0, level - 1) * 5 : 0.0f;
    }
}
