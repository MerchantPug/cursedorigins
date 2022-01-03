package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    private void makeImmuneToHealthIncreases(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (CursedPowers.GLASS.isActive(this)) {
            StatusEffect statusEffect = effect.getEffectType();
            if (statusEffect == StatusEffects.ABSORPTION || statusEffect == StatusEffects.HEALTH_BOOST) {
                cir.setReturnValue(false);
            }
        }
    }

    @ModifyArg(method = "handleFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private DamageSource source(DamageSource source) {
        if (CursedPowers.GLASS.isActive(this)) {
            source = CursedDamageSources.GLASS_FALL;
        }
        return source;
    }

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void setSprinting(boolean sprinting, CallbackInfo ci) {
        if(CursedPowers.CANT_SPRINT.isActive(this)) {
            ci.cancel();
        }
    }
}
