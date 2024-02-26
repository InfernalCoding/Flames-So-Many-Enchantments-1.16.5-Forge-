package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.QuickChargeEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedQuickCharge extends QuickChargeEnchantment {

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public AdvancedQuickCharge() {
        super(Rarity.RARE, EquipmentSlotType.MAINHAND);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return (int) (super.getMinEnchantability(p_77321_1_) * 1.5);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return (int) (super.getMaxEnchantability(p_223551_1_) * 1.5);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.QUICK_CHARGE && ench != Registry.SUPREME_QUICK_CHARGE.get();
    }
}
