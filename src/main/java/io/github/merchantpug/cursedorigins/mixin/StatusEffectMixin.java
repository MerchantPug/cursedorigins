package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import io.github.merchantpug.cursedorigins.registry.CursedSounds;
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
            source.playSound(CursedSounds.ENTITY_GLASS_GOLEM_2_BONG, 1.0F, 1.0F);
            args.set(0, CursedDamageSources.cannonMagic(source, attacker));
            args.set(1, 9999.0F);
        }
    }
}
