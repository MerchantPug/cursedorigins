package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import io.github.merchantpug.cursedorigins.registry.CursedSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntEntity.class)
public abstract class TntEntityMixin extends Entity {
    @Shadow
    private LivingEntity causingEntity;

    public TntEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "explode", at = @At(value = "HEAD"), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/entity/TntEntity;world:Lnet/minecraft/world/World;")))
    private void changeExplosionArgs(CallbackInfo ci) {
        if (CursedPowers.CANNON.isActive(causingEntity)) {
            world.createExplosion(this, CursedDamageSources.cannonExplosion(causingEntity), null, this.getX(), this.getBodyY(0.0625D), this.getZ(), 4.0F, false, Explosion.DestructionType.BREAK);
        }
    }
}
