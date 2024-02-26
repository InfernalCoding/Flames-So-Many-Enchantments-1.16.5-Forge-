package dev.infernal_coding.flamesenchants.enchantments.normal;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedSmite extends DamageEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedSmite() {
        super(Rarity.RARE, 1, EquipmentSlotType.MAINHAND);
    }

    @Override
    public int getMinEnchantability(int level) {
        return (int) (super.getMinEnchantability(level) * 1.5);
    }

    @Override
    public int getMaxEnchantability(int level) {
        return (int) (super.getMaxEnchantability(level) * 1.5);
    }

    @Override
    public float calcDamageByCreature(int level, CreatureAttribute attribute) {
        return attribute == CreatureAttribute.UNDEAD ? 15 + Math.max(0, level - 1) * 3.7f : 0.0f;
    }
}
