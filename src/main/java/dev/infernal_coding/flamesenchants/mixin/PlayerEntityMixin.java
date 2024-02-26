package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.MixinCalls;
import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

import static net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {


    @Shadow @Final public PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Redirect(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getDistanceSq(Lnet/minecraft/entity/Entity;)D"))
    public double $addSweepingRange(PlayerEntity instance, Entity entity) {
        double distance = instance.getDistanceSq(entity);

        int i2 = getMaxEnchantmentLevel(Registry.ADVANCED_SWEEPING.get(), instance);
        int i3 = getMaxEnchantmentLevel(Registry.SUPREME_SWEEPING.get(), instance);

        if (i3 > 0) {
            return distance - 2.55 - 1.25 * i3;
        } else if (i2 > 0) {
            return distance - .75 * i2;
        } else return distance;
    }

    /**
     * @author Infernal_Coding
     * @reason To make advanced/supreme aqua affinity work
     */
    @Overwrite
    public float getDigSpeed(BlockState state, @Nullable BlockPos pos) {
        float f = this.inventory.getDestroySpeed(state);
        if (f > 1.0F) {
            f = MixinCalls.addEfficiency((PlayerEntity) (Object) this, f);
        }

        if (EffectUtils.hasMiningSpeedup(this)) {
            f *= 1.0F + (float)(EffectUtils.getMiningSpeedup(this) + 1) * 0.2F;
        }

        if (this.isPotionActive(Effects.MINING_FATIGUE)) {
            float f1;
            switch(this.getActivePotionEffect(Effects.MINING_FATIGUE).getAmplifier()) {
                case 0:
                    f1 = 0.3F;
                    break;
                case 1:
                    f1 = 0.09F;
                    break;
                case 2:
                    f1 = 0.0027F;
                    break;
                case 3:
                default:
                    f1 = 8.1E-4F;
            }

            f *= f1;
        }

        boolean flag = getMaxEnchantmentLevel(Enchantments.AQUA_AFFINITY, this) > 0
                || getMaxEnchantmentLevel(Registry.ADVANCED_AQUA_AFFINITY.get(), this) > 0
                || getMaxEnchantmentLevel(Registry.SUPREME_AQUA_AFFINITY.get(), this) > 0;

        if (this.areEyesInFluid(FluidTags.WATER) && !flag) {
            f /= 5.0F;
        }

        if (getMaxEnchantmentLevel(Registry.SUPREME_AQUA_AFFINITY.get(), this) > 0) {
            f *= 5.0F;
        } else if (getMaxEnchantmentLevel(Registry.ADVANCED_AQUA_AFFINITY.get(), this) > 0) {
            f *= 2.5F;
        }

        if (!this.onGround) {
            f /= 5.0F;
        }

        f = net.minecraftforge.event.ForgeEventFactory.getBreakSpeed((PlayerEntity) (Object) this, state, f, pos);
        return f;
    }

}
