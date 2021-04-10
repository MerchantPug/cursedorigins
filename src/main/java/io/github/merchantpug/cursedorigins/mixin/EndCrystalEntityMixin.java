package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntityMixin extends Entity {
    public EndCrystalEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/entity/decoration/EndCrystalEntity;world:Lnet/minecraft/world/World;")))
    private void changeExplosionArgs(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (CursedPowers.CANNON.isActive(source.getAttacker())) {
            world.createExplosion(this, CursedDamageSources.cannonCrystalExplosion((LivingEntity)source.getAttacker()), null, this.getX(), this.getY(), this.getZ(), 6.0F, false, Explosion.DestructionType.DESTROY);
        }
    }
}
