package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(StatusEffect.class)
public class StatusEffectMixin {

    @ModifyArgs(method = "applyInstantEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void applyInstantEffect(Args args, Entity source, Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (CursedPowers.GLASS.isActive(attacker)) {
            args.set(0, CursedDamageSources.cannonMagic(source, attacker));
            args.set(1, 9999.0F);
        }
    }
}
