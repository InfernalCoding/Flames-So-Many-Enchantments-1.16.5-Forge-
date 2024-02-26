package dev.infernal_coding.flamesenchants.enchantments.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.*;

public abstract class Rune extends Enchantment {

    protected Rune(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
    }

    public ITextComponent getDisplayName(int i) {
        return super.getDisplayName(i)
                .copyRaw()
                .setStyle(super.getDisplayName(i)
                        .getStyle()
                        .setColor(Color.fromTextFormatting(TextFormatting.GREEN)));
    }

}
