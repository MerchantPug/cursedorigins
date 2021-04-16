package io.github.merchantpug.cursedorigins.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (source.getName().equals("chargedCreeperExplosion.player") || source.getName().equals("chargedCreeperExplosion.player.item")) {
            this.dropItem(Items.CREEPER_HEAD);
        }
    }
}
