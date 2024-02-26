package dev.infernal_coding.flamesenchants.mixin;

import net.minecraft.util.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CombatRules.class)
public class CombatRulesMixin {

    /**
     * @author Infernal_Coding
     * @reason To change the formula for damage calculation
     */
    @Overwrite
    public static float getDamageAfterMagicAbsorb(float damage, float enchantModifiers) {
        if (enchantModifiers <= 20) {
            return damage * (1.0F - enchantModifiers / 25.0F);
        }
        enchantModifiers -= 20;
        damage *= (1.0F - 20 / 25.0F);

        if (enchantModifiers > 20) {
            enchantModifiers -= 20;
            damage *= .8F;
        }

        enchantModifiers *= .25F;
        return damage * (1.0F - enchantModifiers / 25.0F);
    }

}
