package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.PiercingEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PiercingEnchantment.class)
public abstract class PiercingEnchantmentMixin extends Enchantment {

    protected PiercingEnchantmentMixin(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
    }

    /**
     * @author Infernal_Coding
     * @reason Prevent combing piercing and higher-tier multishot
     */
    @Overwrite
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench)
                && ench != Enchantments.MULTISHOT
                && ench != Registry.ADVANCED_MULTISHOT.get()
                && ench != Registry.SUPREME_MULTISHOT.get();
    }
}
