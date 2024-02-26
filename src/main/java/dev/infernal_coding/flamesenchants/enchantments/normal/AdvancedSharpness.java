package dev.infernal_coding.flamesenchants.enchantments.normal;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedSharpness extends DamageEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedSharpness() {
        super(Rarity.RARE, 0, EquipmentSlotType.MAINHAND);
    }

    @Override
    public int getMinEnchantability(int level) {
        return (int) (super.getMinEnchantability(level) * 1.65);
    }

    @Override
    public int getMaxEnchantability(int level) {
        return (int) (super.getMaxEnchantability(level) * 1.65);
    }

    @Override
    public float calcDamageByCreature(int level, CreatureAttribute p_152376_2_) {
        return 3.5f + Math.max(0, level - 1) * .75f;
    }
}
