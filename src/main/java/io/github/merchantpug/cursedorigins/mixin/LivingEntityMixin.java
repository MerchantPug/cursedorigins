package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "addStatusEffect", at = @At("HEAD"), cancellable = true)
    private void addStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (CursedPowers.CALCIUM.isActive(this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isAffectedBySplashPotions", at = @At("HEAD"), cancellable = true)
    private void isAffectedBySplashPotions(CallbackInfoReturnable<Boolean> cir) {
        if (CursedPowers.CALCIUM.isActive(this)) {
            cir.setReturnValue(false);
        }
    }
}
