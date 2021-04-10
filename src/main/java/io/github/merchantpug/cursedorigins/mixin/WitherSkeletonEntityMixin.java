package io.github.merchantpug.cursedorigins.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntity {
    protected WitherSkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (source.getName().equals("chargedCreeperExplosion.player") || source.getName().equals("chargedCreeperExplosion.player.item")) {
            this.dropItem(Items.WITHER_SKELETON_SKULL);
        }
    }
}
