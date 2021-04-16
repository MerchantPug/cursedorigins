package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedEffects;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Nameable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, CommandOutput {


    @Inject(method = "onStruckByLightning", at = @At("HEAD"))
    private void onStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if ((Entity)(Object)this instanceof LivingEntity) {
            if (CursedPowers.THIS_IS_A_SECRET_TO_ALL.isActive((LivingEntity)(Object)this)) {
                ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(CursedEffects.CHARGED, 48000, 0));
            }
        }
    }
}
