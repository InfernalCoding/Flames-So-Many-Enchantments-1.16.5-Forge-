package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.MixinCalls;
import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {

    @Shadow public int xpValue;

    @Shadow protected abstract int durabilityToXp(int durability);



    /**
     * @author Infernal_Coding
     * @reason To make higher tier mending
     */
    @Overwrite
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        MixinCalls.onCollideWithPlayer(entityIn, (ExperienceOrbEntity) (Object) this);
    }
}
