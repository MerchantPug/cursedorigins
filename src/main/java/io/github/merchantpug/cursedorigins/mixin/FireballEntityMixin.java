package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import io.github.merchantpug.cursedorigins.registry.CursedSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(FireballEntity.class)
public class FireballEntityMixin extends AbstractFireballEntity {

    public FireballEntityMixin(EntityType<? extends AbstractFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyArgs(method = "onEntityHit", at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void modifyEntityHit(Args args) {
        if (this.getOwner() != null && CursedPowers.CANNON.isActive(this.getOwner())) {
            this.getOwner().playSound(CursedSounds.ENTITY_GLASS_GOLEM_2_BONG, 1.0F, 1.0F);
            args.set(0, CursedDamageSources.cannonFireball(this, this.getOwner()));
            args.set(1, 9999.0F);
        }
    }
}
