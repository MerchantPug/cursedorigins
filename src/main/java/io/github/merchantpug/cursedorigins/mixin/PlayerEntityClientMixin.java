package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedEffects;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityClientMixin extends LivingEntity implements SkinOverlayOwner {

    public PlayerEntityClientMixin(World world) {
        super(EntityType.PLAYER, world);
    }

    @Unique
    public boolean shouldRenderOverlay() {
        if (CursedPowers.THIS_IS_A_SECRET_TO_ALL.isActive(this)) {
            return this.hasStatusEffect(CursedEffects.CHARGED);
        } else {
            return false;
        }
    }
}
