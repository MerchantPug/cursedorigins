package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import io.github.merchantpug.cursedorigins.registry.CursedSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "onEntityHit", at = @At("STORE"))
    private DamageSource modifyDamageSource(DamageSource damageSource, EntityHitResult entityHitResult) {
        if (this.getOwner() != null && CursedPowers.CANNON.isActive(this.getOwner())) {
            this.playSound(CursedSounds.ENTITY_GLASS_GOLEM_2_BONG, 1.0F, 1.0F);
            damageSource = CursedDamageSources.cannonTrident(this, this.getOwner());
        }
        return damageSource;
    }

    @ModifyVariable(method = "onEntityHit", at = @At("STORE"))
    private float changeF(float f, EntityHitResult entityHitResult) {
        if (CursedPowers.CANNON.isActive(this.getOwner())) {
            f = 9999.0F;
        }
        return f;
    }
}
