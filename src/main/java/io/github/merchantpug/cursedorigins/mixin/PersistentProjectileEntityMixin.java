package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import io.github.merchantpug.cursedorigins.registry.CursedSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "onEntityHit", at = @At(value = "STORE"))
    private int changeDamageAmount(int i) {
        if (this.getOwner() != null && CursedPowers.CANNON.isActive(this.getOwner())) {
            this.playSound(CursedSounds.ENTITY_GLASS_GOLEM_2_BONG, 1.0F, 1.0F);
            return i = 9999;
        }
        return i;
    }

    @ModifyVariable(method = "onEntityHit", at = @At(value = "STORE"))
    private DamageSource changeDamageSource(DamageSource damageSource2) {
        if (CursedPowers.CANNON.isActive(this.getOwner())) {
            return damageSource2 = CursedDamageSources.cannonArrow(this, this.getOwner());
        }
        return damageSource2;
    }
}
