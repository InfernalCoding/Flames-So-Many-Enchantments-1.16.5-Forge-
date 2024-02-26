package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel;

@Mixin(AbstractArrowEntity.class)
public abstract class AbstractArrowEntityMixin extends Entity {

    public AbstractArrowEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Shadow public abstract void setDamage(double damageIn);

    @Shadow public abstract double getDamage();

    @Shadow public abstract void setKnockbackStrength(int knockbackStrengthIn);

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of flame
     */
    @Overwrite
    public void setEnchantmentEffectsFromEntity(LivingEntity p_190547_1_, float p_190547_2_) {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_POWER.get(), p_190547_1_);


        int j = getPunch(p_190547_1_);
        this.setDamage((double)(p_190547_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getId() * 0.11F));

        if (i2 > 0) {
            this.setDamage(this.getDamage() + (double)i * 0.75D + 0.5D);
        } else if (i > 0) {
            this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0) {
            this.setKnockbackStrength(j);
        }

        int level1 = getMaxEnchantmentLevel(Registry.SUPREME_FLAME.get(), p_190547_1_);
        int level2 = getMaxEnchantmentLevel(Registry.ADVANCED_FLAME.get(), p_190547_1_);
        int level3 = getMaxEnchantmentLevel(Enchantments.FLAME, p_190547_1_);

        if (level1 > 0) {
            this.setFire(400);
        } else if (level2 > 0) {
            this.setFire(200);
        } else if (level3 > 0) {
            this.setFire(100);
        }

    }

    @Unique
    private static int getPunch(LivingEntity entity) {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, entity);
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_PUNCH.get(), entity);
        int i3 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.SUPREME_PUNCH.get(), entity);

        if (i3 > 0) {
            return 6 + i3 * 4;
        } else if (i2 > 0) {
           return 2 + i2 * 2;
        } else return i;
    }
}
