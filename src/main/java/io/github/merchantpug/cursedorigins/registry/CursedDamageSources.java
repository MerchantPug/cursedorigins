package io.github.merchantpug.cursedorigins.registry;

import io.github.apace100.calio.mixin.DamageSourceAccessor;
import io.github.merchantpug.cursedorigins.access.DamageSourceAccess;
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

    public static final DamageSource GLASS_FALL = ((DamageSourceAccess)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("glassFall")).callSetBypassesArmor()).setGlassGolemSource();

    public static DamageSource cannonPlayer(Entity attacker) {
        return ((DamageSourceAccess)((DamageSourceAccessor)new EntityDamageSource("cannonPlayer", attacker)).callSetBypassesArmor()).setGlassGolemSource();
    }

    public static DamageSource cannonArrow(ProjectileEntity projectile, Entity attacker) {
        return ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("cannonArrow", projectile, attacker)).callSetBypassesArmor().setProjectile()).setGlassGolemSource();
    }

    public static DamageSource cannonTrident(Entity trident, Entity attacker) {
        return ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("cannonTrident", trident, attacker)).callSetBypassesArmor().setProjectile()).setGlassGolemSource();
    }

    public static DamageSource cannonMagic(Entity magic, Entity attacker) {
        return ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("cannonMagic", magic, attacker)).callSetBypassesArmor().setUsesMagic()).setGlassGolemSource();
    }

    public static DamageSource cannonFirework(ProjectileEntity firework, Entity attacker) {
        return ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("cannonFirework", firework, attacker)).callSetBypassesArmor()).setGlassGolemSource().setProjectile().setExplosive();
    }

    public static DamageSource cannonEggProjectile(Entity projectile, Entity attacker) {
        return ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("cannonEgg", projectile, attacker)).callSetBypassesArmor()).setGlassGolemSource().setProjectile();
    }

    public static DamageSource cannonSnowballProjectile(Entity projectile, Entity attacker) {
        return ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("cannonSnowball", projectile, attacker)).callSetBypassesArmor()).setGlassGolemSource().setProjectile();
    }

    public static DamageSource cannonEnderPearlProjectile(Entity projectile, Entity attacker) {
        return ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("cannonPearl", projectile, attacker)).callSetBypassesArmor()).setGlassGolemSource().setProjectile();
    }

    public static DamageSource cannonFireball(AbstractFireballEntity fireball, Entity attacker) {
        return attacker == null ? ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("onFire", fireball, fireball)).callSetFire()).setGlassGolemSource().setProjectile() : ((DamageSourceAccess)((DamageSourceAccessor)new ProjectileDamageSource("cannonFireball", fireball, attacker)).callSetFire()).setGlassGolemSource().setProjectile();
    }

    public static DamageSource cannonBadRespawnPoint() {
        return new CannonBadRespawnPointDamageSource();
    }

    public static DamageSource cannonExplosion(Explosion cannonExplosion) {
        return cannonExplosion(cannonExplosion != null ? cannonExplosion.getCausingEntity() : null);
    }

    public static DamageSource cannonExplosion(LivingEntity attacker) {
        return attacker != null ? ((DamageSourceAccess)((DamageSourceAccessor)new EntityDamageSource("cannonExplosion.player", attacker)).callSetBypassesArmor()).setGlassGolemSource().setScaledWithDifficulty().setExplosive() : ((DamageSourceAccess)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("cannonExplosion")).callSetBypassesArmor()).setGlassGolemSource().setExplosive();
    }

    public static DamageSource cannonCrystalExplosion(Explosion cannonCrystalExplosion) {
        return cannonCrystalExplosion(cannonCrystalExplosion != null ? cannonCrystalExplosion.getCausingEntity() : null);
    }

    public static DamageSource cannonCrystalExplosion(LivingEntity attacker) {
        return attacker != null ? ((DamageSourceAccess)((DamageSourceAccessor)new EntityDamageSource("cannonCrystalExplosion.player", attacker)).callSetBypassesArmor()).setGlassGolemSource().setScaledWithDifficulty().setExplosive() : ((DamageSourceAccess)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("cannonCrystalExplosion")).callSetBypassesArmor()).setGlassGolemSource().setExplosive();
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
