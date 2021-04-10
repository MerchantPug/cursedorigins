package io.github.merchantpug.cursedorigins.power;

import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.merchantpug.cursedorigins.mixin.ServerPlayerEntityAccessor;
import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedEffects;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.explosion.Explosion;

import java.util.Collection;

public class SelfExplodePower extends Power implements Active {
    private final float explosionRadius;
    private final boolean spawnsEffectCloud;
    private Key key;

    public SelfExplodePower(PowerType<?> type, PlayerEntity player, float explosionRadius, boolean spawnsEffectCloud) {
        super(type, player);
        this.explosionRadius = explosionRadius;
        this.spawnsEffectCloud = spawnsEffectCloud;
    }

    @Override
    public void onUse() {
        if (!player.world.isClient) {
            if (!player.abilities.invulnerable && ((ServerPlayerEntityAccessor)(ServerPlayerEntity)player).getJoinInvulnerabilityTicks() <= 0 && !player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                DamageSource damageSource = player.hasStatusEffect(CursedEffects.CHARGED) ? CursedDamageSources.chargedExplosion(player) : CursedDamageSources.creeperExplosion(player);
                float f = player.hasStatusEffect(CursedEffects.CHARGED) ? 2.0F : 1.0F;
                Explosion.DestructionType destructionType = player.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;

                player.world.createExplosion(player, damageSource, null, player.getX(), player.getY(), player.getZ(), explosionRadius * f, false, destructionType);

                if (player.hasStatusEffect(CursedEffects.CHARGED)) {
                    player.removeStatusEffect(CursedEffects.CHARGED);
                }

                Collection<StatusEffectInstance> collection = player.getStatusEffects();
                if (!collection.isEmpty()) {
                    AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(player.world, player.getX(), player.getY(), player.getZ());
                    areaEffectCloudEntity.setRadius(2.5F);
                    areaEffectCloudEntity.setRadiusOnUse(-0.5F);
                    areaEffectCloudEntity.setWaitTime(10);
                    areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
                    areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());

                    for (StatusEffectInstance statusEffectInstance : collection) {
                        areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
                    }
                    player.world.spawnEntity(areaEffectCloudEntity);
                }
                player.damage(CursedDamageSources.CREEPER_SELF, 9999.0F);
            }
        }
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }
}
