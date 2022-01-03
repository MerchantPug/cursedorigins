package io.github.merchantpug.cursedorigins.mixin;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.origin.OriginRegistry;
import io.github.apace100.origins.registry.ModComponents;
import io.github.merchantpug.cursedorigins.CursedOrigins;
import io.github.merchantpug.cursedorigins.access.DamageSourceAccess;
import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedItems;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Unique
    private DamageSource recentDamageSource;

    @Shadow public abstract void sendMessage(Text message, boolean actionBar);
    @Shadow public abstract ItemEntity dropItem(ItemStack stack, boolean retainOwnership);

    @Shadow public abstract boolean isInvulnerableTo(DamageSource damageSource);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "attack", at = @At("STORE"))
    private boolean bl6(boolean bl6, Entity target) {
        if (CursedPowers.CANNON.isActive(this)) {
            return bl6 = target.damage(CursedDamageSources.cannonPlayer(this), (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
        }
        return bl6;
    }

    @Inject(method = "damage", at = @At(value = "RETURN", ordinal = 4))
    private void captureDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        recentDamageSource = source;
    }

    @Inject(method = "damageArmor", at = @At("HEAD"), cancellable = true)
    private void cancelArmorDamage(DamageSource source, float amount, CallbackInfo ci) {
        if (((DamageSourceAccess)source).isGlassGolemSource() || CursedPowers.GLASS.isActive((PlayerEntity)(Object)this)) ci.cancel();
    }

    @Inject(method = "damageHelmet", at = @At("HEAD"), cancellable = true)
    private void cancelHelmetDamage(DamageSource source, float amount, CallbackInfo ci) {
        if (((DamageSourceAccess)source).isGlassGolemSource() || CursedPowers.GLASS.isActive((PlayerEntity)(Object)this)) ci.cancel();
    }

    @Inject(method = "damageShield", at = @At("HEAD"), cancellable = true)
    private void cancelShieldDamage(float amount, CallbackInfo ci) {
        if (((DamageSourceAccess)recentDamageSource).isGlassGolemSource() || CursedPowers.GLASS.isActive((PlayerEntity)(Object)this)) ci.cancel();
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    private void changeToLiteralCreeper(DamageSource source, float amount, CallbackInfo ci) {
        Text text = new TranslatableText("chat.creeperalert");
        if (this.world.isClient || this.isInvulnerableTo(source)) return;
        if (source.getName().equals("creeperExplosion.player") || source.getName().equals("chargedCreeperExplosion.player.item") || source.getName().equals("chargedCreeperExplosion.player") || source.getName().equals("chargedCreeperExplosion.player.item")) {
            if (!ModComponents.ORIGIN.get(this).getOrigin(OriginLayers.getLayer(Origins.identifier("origin"))).equals(OriginRegistry.get(CursedOrigins.identifier("literal_creeper")))) {
                ModComponents.ORIGIN.get(this).setOrigin(OriginLayers.getLayer(Origins.identifier("origin")), OriginRegistry.get(CursedOrigins.identifier("literal_creeper")));
                OriginComponent.sync((PlayerEntity) (Object) this);
                this.sendMessage(text, false);
            }
        }
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void soundEvent(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        if (CursedPowers.COMEDY.isActive(this)) {
            cir.setReturnValue(SoundEvents.BLOCK_GLASS_BREAK);
        } else if (CursedPowers.THIS_IS_A_SECRET_TO_ALL.isActive(this)) {
            cir.setReturnValue(SoundEvents.ENTITY_CREEPER_HURT);
        }
    }

    @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
    private void replaceDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (CursedPowers.COMEDY.isActive(this)) {
            cir.setReturnValue(SoundEvents.BLOCK_GLASS_BREAK);
        } else if (CursedPowers.THIS_IS_A_SECRET_TO_ALL.isActive(this)) {
            cir.setReturnValue(SoundEvents.ENTITY_CREEPER_DEATH);
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void creeperDisc(DamageSource source, CallbackInfo ci) {
        if (CursedPowers.THIS_IS_A_SECRET_TO_ALL.isActive(this)) {
            if (source.getName().equals("arrow") && source.getAttacker() instanceof AbstractSkeletonEntity || CursedPowers.THIS_IS_A_SECRET_TO_ALL.isActive(this) && (source.getName().equals("arrow.item")) && source.getAttacker() instanceof AbstractSkeletonEntity) {
                this.dropItem(CursedItems.CREEPER_DISC.getDefaultStack(), false);
            }
            if (!this.world.isClient && source.getAttacker() instanceof PlayerEntity && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                int i;
                for (i = 0; i < 3; ++i) {
                    this.dropItem(Items.GUNPOWDER.getDefaultStack(), false);
                }
            }
        }
    }
}
