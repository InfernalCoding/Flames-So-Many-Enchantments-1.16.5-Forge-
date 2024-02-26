package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends AbstractArrowEntity {

    @Shadow private boolean dealtDamage;

    @Shadow private ItemStack thrownStack;

    protected TridentEntityMixin(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void onEntityHit(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        float f = 8.0F;
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            f += EnchantmentHelper.getModifierForCreature(this.thrownStack, livingentity.getCreatureAttribute());
        }

        Entity entity1 = this.getShooter();
        DamageSource damagesource = DamageSource.causeTridentDamage(this, (Entity)(entity1 == null ? this : entity1));
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.ITEM_TRIDENT_HIT;
        if (entity.attackEntityFrom(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity1, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity1);
                }

                this.arrowHit(livingentity1);
            }
        }

        this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;
        if (this.world instanceof ServerWorld && this.world.isThundering()) {

           if (hasSupremeChanneling(this.thrownStack)) {
               BlockPos blockpos = entity.getPosition();
               if (this.world.canSeeSky(blockpos)) {
                   LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.world);
                   lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(blockpos));
                   lightningboltentity.setCaster(entity1 instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity1 : null);
                   this.world.addEntity(lightningboltentity);
                   soundevent = SoundEvents.ITEM_TRIDENT_THUNDER;
                   f1 = 25.0F;
               }
           } else if (hasAdvancedChanneling(this.thrownStack)) {
               BlockPos blockpos = entity.getPosition();
               if (this.world.canSeeSky(blockpos)) {
                   LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.world);
                   lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(blockpos));
                   lightningboltentity.setCaster(entity1 instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity1 : null);
                   this.world.addEntity(lightningboltentity);
                   soundevent = SoundEvents.ITEM_TRIDENT_THUNDER;
                   f1 = 15.0F;
               }
           } else if (EnchantmentHelper.hasChanneling(this.thrownStack)) {
                BlockPos blockpos = entity.getPosition();
                if (this.world.canSeeSky(blockpos)) {
                    LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.world);
                    lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(blockpos));
                    lightningboltentity.setCaster(entity1 instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity1 : null);
                    this.world.addEntity(lightningboltentity);
                    soundevent = SoundEvents.ITEM_TRIDENT_THUNDER;
                    f1 = 5.0F;
                }
            }
        }
        this.playSound(soundevent, f1, 1.0F);
    }

    private static boolean hasAdvancedChanneling(ItemStack stack) {
        return getEnchantmentLevel(Registry.ADVANCED_CHANNELING.get(), stack) > 0;
    }

    private static boolean hasSupremeChanneling(ItemStack stack) {
        return getEnchantmentLevel(Registry.SUPREME_CHANNELING.get(), stack) > 0;
    }
}
