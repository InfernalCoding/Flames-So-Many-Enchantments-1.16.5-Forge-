package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.RiptideEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RiptideEnchantment.class)
public abstract class RiptideEnchantmentMixin extends Enchantment {

    protected RiptideEnchantmentMixin(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Redirect(method = "canApplyTogether", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canApplyTogether(Lnet/minecraft/enchantment/Enchantment;)Z"))
    private boolean noRiptide(Enchantment instance, Enchantment ench) {
        return super.canApplyTogether(ench) &&
                (ench != Registry.ADVANCED_CHANNELING.get() && ench != Registry.SUPREME_CHANNELING.get());
    }
}
