package io.github.merchantpug.cursedorigins.registry;

import io.github.apace100.origins.mixin.DamageSourceAccessor;
import io.github.merchantpug.cursedorigins.entity.damage.CannonBadRespawnPointDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.explosion.Explosion;

public class CursedDamageSources {

    public static final DamageSource GLASS_FALL = (((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("glassFall")).callSetBypassesArmor());
    public static final DamageSource CREEPER_SELF = (((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("creeperSelf")).callSetBypassesArmor());

    public static DamageSource cannonPlayer(Entity attacker) {
        return ((DamageSourceAccessor)new EntityDamageSource("cannonPlayer", attacker)).callSetBypassesArmor();
    }

    public static DamageSource cannonArrow(ProjectileEntity projectile, Entity attacker) {
        return ((DamageSourceAccessor)new ProjectileDamageSource("cannonArrow", projectile, attacker)).callSetBypassesArmor().setProjectile();
    }

    public static DamageSource cannonTrident(Entity trident, Entity attacker) {
        return ((DamageSourceAccessor)new ProjectileDamageSource("cannonTrident", trident, attacker)).callSetBypassesArmor().setProjectile();
    }

    public static DamageSource cannonMagic(Entity magic, Entity attacker) {
        return ((DamageSourceAccessor)new ProjectileDamageSource("cannonMagic", magic, attacker)).callSetBypassesArmor().setUsesMagic();
    }

    public static DamageSource cannonFirework(ProjectileEntity firework, Entity attacker) {
        return ((DamageSourceAccessor)new ProjectileDamageSource("cannonFirework", firework, attacker)).callSetBypassesArmor().setProjectile().setExplosive();
    }

    public static DamageSource cannonEggProjectile(Entity projectile, Entity attacker) {
        return ((DamageSourceAccessor)new ProjectileDamageSource("cannonEgg", projectile, attacker)).callSetBypassesArmor().setProjectile();
    }

    public static DamageSource cannonSnowballProjectile(Entity projectile, Entity attacker) {
        return ((DamageSourceAccessor)new ProjectileDamageSource("cannonSnowball", projectile, attacker)).callSetBypassesArmor().setProjectile();
    }

    public static DamageSource cannonEnderPearlProjectile(Entity projectile, Entity attacker) {
        return ((DamageSourceAccessor)new ProjectileDamageSource("cannonPearl", projectile, attacker)).callSetBypassesArmor().setProjectile();
    }

    public static DamageSource cannonFireball(AbstractFireballEntity fireball, Entity attacker) {
        return attacker == null ? ((DamageSourceAccessor)new ProjectileDamageSource("onFire", fireball, fireball)).callSetFire().setProjectile() : ((DamageSourceAccessor)new ProjectileDamageSource("cannonFireball", fireball, attacker)).callSetFire().setProjectile();
    }

    public static DamageSource cannonBadRespawnPoint() {
        return new CannonBadRespawnPointDamageSource();
    }

    public static DamageSource cannonExplosion(Explosion cannonExplosion) {
        return cannonExplosion(cannonExplosion != null ? cannonExplosion.getCausingEntity() : null);
    }

    public static DamageSource cannonExplosion(LivingEntity attacker) {
        return attacker != null ? ((DamageSourceAccessor)new EntityDamageSource("cannonExplosion.player", attacker)).callSetBypassesArmor().setScaledWithDifficulty().setExplosive() : (((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("cannonExplosion")).callSetBypassesArmor().setExplosive());
    }

    public static DamageSource cannonCrystalExplosion(Explosion cannonCrystalExplosion) {
        return cannonCrystalExplosion(cannonCrystalExplosion != null ? cannonCrystalExplosion.getCausingEntity() : null);
    }

    public static DamageSource cannonCrystalExplosion(LivingEntity attacker) {
        return attacker != null ? ((DamageSourceAccessor)new EntityDamageSource("cannonCrystalExplosion.player", attacker)).callSetBypassesArmor().setScaledWithDifficulty().setExplosive() : (((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("cannonCrystalExplosion")).callSetBypassesArmor().setExplosive());
    }

    public static DamageSource creeperExplosion(Explosion creeperExplosion) {
        return creeperExplosion(creeperExplosion != null ? creeperExplosion.getCausingEntity() : null);
    }

    public static DamageSource creeperExplosion(LivingEntity attacker) {
        return attacker != null ? (new EntityDamageSource("creeperExplosion.player", attacker)).setScaledWithDifficulty().setExplosive() : ((DamageSourceAccessor.createDamageSource("creeperExplosion")).setExplosive());
    }

    public static DamageSource chargedExplosion(Explosion chargedExplosion) {
        return chargedExplosion(chargedExplosion != null ? chargedExplosion.getCausingEntity() : null);
    }

    public static DamageSource chargedExplosion(LivingEntity attacker) {
        return attacker != null ? (new EntityDamageSource("chargedCreeperExplosion.player", attacker)).setScaledWithDifficulty().setExplosive() : ((DamageSourceAccessor.createDamageSource("chargedCreeperExplosion")).setExplosive());
    }
}
