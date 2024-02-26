package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FrostWalkerEnchantment.class)
public abstract class FrostWalkerEnchantmentMixin extends Enchantment {

    protected FrostWalkerEnchantmentMixin(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    /**
     * @author Infernal_Coding
     * @reason To make depth strider and frost walker clash when enchanting
     */
    @Overwrite
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.DEPTH_STRIDER
                && ench != Registry.ADVANCED_DEPTH_STRIDER.get() && ench != Registry.SUPREME_DEPTH_STRIDER.get();
    }

}
