package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Redirect(method = "attackEntityAsMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFire(I)V"))
    public void setFire(Entity instance, int seconds) {
        LivingEntity entity = (LivingEntity) (Object) this;

        int level1 = getMaxEnchantmentLevel(Registry.SUPREME_FIRE_ASPECT.get(), entity);
        int level2 = getMaxEnchantmentLevel(Registry.ADVANCED_FIRE_ASPECT.get(), entity);

        if (level1 > 0) {
            entity.setFire(6 + level1 * 3);
        } else if (level2 > 0) {
            entity.setFire(2 + level2 * 2);
        } else entity.setFire(seconds);
    }
}
