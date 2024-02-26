package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.fluid.FluidState;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

   @Shadow @Final private static AttributeModifier SLOW_FALLING;


    public LivingEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Shadow protected abstract boolean func_241208_cS_();

    @Shadow protected abstract float getWaterSlowDown();

    @Shadow protected abstract SoundEvent getFallSound(int heightIn);


    @Shadow protected abstract boolean func_230295_b_(BlockState p_230295_1_);

    @Shadow protected abstract void func_233641_cN_();

    @Shadow protected abstract void func_233642_cO_();

    @Shadow protected abstract int determineNextAir(int currentAir);

    @Redirect(method = "getSpeedFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getMaxEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;)I"))
    private int getSoulLevel(Enchantment p_185284_0_, LivingEntity living) {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.SOUL_SPEED, living);
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_SOUL_SPEED.get(), living);
        int i3 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.SUPREME_SOUL_SPEED.get(), living);

        if (i3 > 0) {
            return i3;
        } else if (i2 > 0) {
            return i2;
        } else return i;
    }

    @Redirect(method = "func_233642_cO_", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getMaxEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;)I"))
    private int getSoulSpeed(Enchantment enchantment, LivingEntity living) {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.SOUL_SPEED, living);
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_SOUL_SPEED.get(), living);
        int i3 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.SUPREME_SOUL_SPEED.get(), living);

        if (i3 > 0) {
            return 9 + i3 * 2;
        } else if (i2 > 0) {
            return 3 + i2 * 2;
        } else return i;
    }

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of respiration
     */
    @Overwrite
    protected int decreaseAirSupply(int air) {

        LivingEntity instance = (LivingEntity) (Object) this;
        int i = EnchantmentHelper.getRespirationModifier(instance);
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_RESPIRATION.get(), instance);
        int i3 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.SUPREME_RESPIRATION.get(), instance);

        if (i3 > 0) {
            return this.rand.nextInt(i + 4) > 0 ? air : air - 1;
        } else if (i2 > 0) {
            return this.rand.nextInt(i + 2) > 0 ? air : air - 1;
        } else return i > 0 && this.rand.nextInt(i + 1) > 0 ? air : air - 1;
    }

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of frost walker
     */
    @Overwrite
    protected void frostWalk(BlockPos pos) {
        LivingEntity entity = (LivingEntity) (Object) this;

        int i1 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.SUPREME_FROST_WALKER.get(), entity);
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_FROST_WALKER.get(), entity);
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FROST_WALKER, entity);

        if (i1 > 0) {
            $freezeNearby(entity, this.world, pos, i1, "supreme");
        } else if (i2 > 0) {
            $freezeNearby(entity, this.world, pos, i2, "advanced");
        } else if (i > 0) {
            $freezeNearby(entity, this.world, pos, i, "normal");
        }

        if (this.func_230295_b_(this.getStateBelow())) {
            this.func_233641_cN_();
        }

        this.func_233642_cO_();
    }

    @Unique
    private void $freezeNearby(LivingEntity living, World worldIn, BlockPos pos, int level, String type) {
        if (living.isOnGround()) {
            BlockState blockstate = Blocks.FROSTED_ICE.getDefaultState();
            float f = (float)Math.min(16, 2 + level);

            if (type.equals("supreme")) {
                f = (float) Math.min(16, 6 + level * 2);
            } else if (type.equals("advanced")) {
                f = (float) Math.min(16, 3 + level * 2);
            }

            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-f, -1.0D, -f), pos.add(f, -1.0D, f))) {
                if (blockpos.withinDistance(living.getPositionVec(), f)) {
                    blockpos$mutable.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = worldIn.getBlockState(blockpos$mutable);
                    if (blockstate1.isAir(worldIn, blockpos$mutable)) {
                        BlockState blockstate2 = worldIn.getBlockState(blockpos);
                        boolean isFull = blockstate2.getBlock() == Blocks.WATER && blockstate2.get(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
                        if (blockstate2.getMaterial() == Material.WATER && isFull && blockstate.isValidPosition(worldIn, blockpos) && worldIn.placedBlockCollides(blockstate, blockpos, ISelectionContext.dummy()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(living, net.minecraftforge.common.util.BlockSnapshot.create(worldIn.getDimensionKey(), worldIn, blockpos), net.minecraft.util.Direction.UP)) {
                            worldIn.setBlockState(blockpos, blockstate);
                            worldIn.getPendingBlockTicks().scheduleTick(blockpos, Blocks.FROSTED_ICE, MathHelper.nextInt(living.getRNG(), 60, 120));
                        }
                    }
                }
            }

        }
    }

    /**
     * @author Infernal_Coding
     * @reason To make higher tiers of depth strider
     */
    @Overwrite
    public void travel(Vector3d travelVector) {
        
        LivingEntity entity = (LivingEntity) (Object) this;
        
        if (entity.isServerWorld() || entity.canPassengerSteer()) {
            double d0 = 0.08D;
            ModifiableAttributeInstance gravity = entity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
            boolean flag = entity.getMotion().y <= 0.0D;
            if (flag && entity.isPotionActive(Effects.SLOW_FALLING)) {
                if (!gravity.hasModifier(SLOW_FALLING)) gravity.applyNonPersistentModifier(SLOW_FALLING);
                entity.fallDistance = 0.0F;
            } else if (gravity.hasModifier(SLOW_FALLING)) {
                gravity.removeModifier(SLOW_FALLING);
            }
            d0 = gravity.getValue();

            FluidState fluidstate = entity.world.getFluidState(entity.getPosition());
            if (entity.isInWater() && this.func_241208_cS_() && !entity.func_230285_a_(fluidstate.getFluid())) {
                double d8 = entity.getPosY();
                float f5 = entity.isSprinting() ? 0.9F : this.getWaterSlowDown();
                float f6 = 0.02F;
                float f7 = (float) getDepthStriderModifier(entity);

                if (!entity.isOnGround()) {
                    f7 *= 0.5F;
                }

                if (f7 > 0.0F) {
                    f5 += (0.54600006F - f5) * f7 / 3.0F;
                    f6 += (entity.getAIMoveSpeed() - f6) * f7 / 3.0F;
                }

                if (entity.isPotionActive(Effects.DOLPHINS_GRACE)) {
                    f5 = 0.96F;
                }

                f6 *= (float)entity.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
                entity.moveRelative(f6, travelVector);
                entity.move(MoverType.SELF, entity.getMotion());
                Vector3d vector3d6 = entity.getMotion();
                if (entity.collidedHorizontally && entity.isOnLadder()) {
                    vector3d6 = new Vector3d(vector3d6.x, 0.2D, vector3d6.z);
                }

                entity.setMotion(vector3d6.mul(f5, 0.8F, f5));
                Vector3d vector3d2 = entity.func_233626_a_(d0, flag, entity.getMotion());
                entity.setMotion(vector3d2);
                if (entity.collidedHorizontally && entity.isOffsetPositionInLiquid(vector3d2.x, vector3d2.y + (double)0.6F - entity.getPosY() + d8, vector3d2.z)) {
                    entity.setMotion(vector3d2.x, 0.3F, vector3d2.z);
                }
            } else if (entity.isInLava() && this.func_241208_cS_() && !entity.func_230285_a_(fluidstate.getFluid())) {
                double d7 = entity.getPosY();
                entity.moveRelative(0.02F, travelVector);
                entity.move(MoverType.SELF, entity.getMotion());
                if (entity.func_233571_b_(FluidTags.LAVA) <= entity.getFluidJumpHeight()) {
                    entity.setMotion(entity.getMotion().mul(0.5D, 0.8F, 0.5D));
                    Vector3d vector3d3 = entity.func_233626_a_(d0, flag, entity.getMotion());
                    entity.setMotion(vector3d3);
                } else {
                    entity.setMotion(entity.getMotion().scale(0.5D));
                }

                if (!entity.hasNoGravity()) {
                    entity.setMotion(entity.getMotion().add(0.0D, -d0 / 4.0D, 0.0D));
                }

                Vector3d vector3d4 = entity.getMotion();
                if (entity.collidedHorizontally && entity.isOffsetPositionInLiquid(vector3d4.x, vector3d4.y + (double)0.6F - entity.getPosY() + d7, vector3d4.z)) {
                    entity.setMotion(vector3d4.x, 0.3F, vector3d4.z);
                }
            } else if (entity.isElytraFlying()) {
                Vector3d vector3d = entity.getMotion();
                if (vector3d.y > -0.5D) {
                    entity.fallDistance = 1.0F;
                }

                Vector3d vector3d1 = entity.getLookVec();
                float f = entity.rotationPitch * ((float)Math.PI / 180F);
                double d1 = Math.sqrt(vector3d1.x * vector3d1.x + vector3d1.z * vector3d1.z);
                double d3 = Math.sqrt(horizontalMag(vector3d));
                double d4 = vector3d1.length();
                float f1 = MathHelper.cos(f);
                f1 = (float)((double)f1 * (double)f1 * Math.min(1.0D, d4 / 0.4D));
                vector3d = entity.getMotion().add(0.0D, d0 * (-1.0D + (double)f1 * 0.75D), 0.0D);
                if (vector3d.y < 0.0D && d1 > 0.0D) {
                    double d5 = vector3d.y * -0.1D * (double)f1;
                    vector3d = vector3d.add(vector3d1.x * d5 / d1, d5, vector3d1.z * d5 / d1);
                }

                if (f < 0.0F && d1 > 0.0D) {
                    double d9 = d3 * (double)(-MathHelper.sin(f)) * 0.04D;
                    vector3d = vector3d.add(-vector3d1.x * d9 / d1, d9 * 3.2D, -vector3d1.z * d9 / d1);
                }

                if (d1 > 0.0D) {
                    vector3d = vector3d.add((vector3d1.x / d1 * d3 - vector3d.x) * 0.1D, 0.0D, (vector3d1.z / d1 * d3 - vector3d.z) * 0.1D);
                }

                entity.setMotion(vector3d.mul(0.99F, 0.98F, 0.99F));
                entity.move(MoverType.SELF, entity.getMotion());
                if (entity.collidedHorizontally && !entity.world.isRemote) {
                    double d10 = Math.sqrt(horizontalMag(entity.getMotion()));
                    double d6 = d3 - d10;
                    float f2 = (float)(d6 * 10.0D - 3.0D);
                    if (f2 > 0.0F) {
                        entity.playSound(this.getFallSound((int)f2), 1.0F, 1.0F);
                        entity.attackEntityFrom(DamageSource.FLY_INTO_WALL, f2);
                    }
                }

                if (entity.isOnGround() && !entity.world.isRemote) {
                    this.setFlag(7, false);
                }
            } else {
                BlockPos blockpos = this.getPositionUnderneath();
                float f3 = entity.world.getBlockState(this.getPositionUnderneath()).getSlipperiness(world, this.getPositionUnderneath(), entity);
                float f4 = this.onGround ? f3 * 0.91F : 0.91F;
                Vector3d vector3d5 = entity.func_233633_a_(travelVector, f3);
                double d2 = vector3d5.y;
                if (entity.isPotionActive(Effects.LEVITATION)) {
                    d2 += (0.05D * (double)(entity.getActivePotionEffect(Effects.LEVITATION).getAmplifier() + 1) - vector3d5.y) * 0.2D;
                    entity.fallDistance = 0.0F;
                } else if (entity.world.isRemote && !entity.world.isBlockLoaded(blockpos)) {
                    if (entity.getPosY() > 0.0D) {
                        d2 = -0.1D;
                    } else {
                        d2 = 0.0D;
                    }
                } else if (!entity.hasNoGravity()) {
                    d2 -= d0;
                }

                entity.setMotion(vector3d5.x * (double)f4, d2 * (double)0.98F, vector3d5.z * (double)f4);
            }
        }

        entity.func_233629_a_(entity, entity instanceof IFlyingAnimal);
    }

    private static int getDepthStriderModifier(LivingEntity entityIn) {

        int i1 = getMaxEnchantmentLevel(Registry.SUPREME_DEPTH_STRIDER.get(), entityIn);
        int i2 = getMaxEnchantmentLevel(Registry.ADVANCED_DEPTH_STRIDER.get(), entityIn);

        if (i1 > 0) {
            return i1 + 3;
        } else if (i2 > 0) {
            return i2 + 2;
        }
        return getMaxEnchantmentLevel(Enchantments.DEPTH_STRIDER, entityIn);
    }
}
