package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import io.github.merchantpug.cursedorigins.registry.CursedDamageSources;
import io.github.merchantpug.cursedorigins.registry.CursedItems;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    private void makeImmuneToHealthIncreases(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (CursedPowers.GLASS.isActive(this)) {
            StatusEffect statusEffect = effect.getEffectType();
            if (statusEffect == StatusEffects.ABSORPTION || statusEffect == StatusEffects.HEALTH_BOOST) {
                cir.setReturnValue(false);
            }
        }
    }

    @ModifyArg(method = "handleFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private DamageSource source(DamageSource source) {
        if (CursedPowers.GLASS.isActive(this)) {
            source = CursedDamageSources.GLASS_FALL;
        }
        return source;
    }

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void setSprinting(boolean sprinting, CallbackInfo ci) {
        if(CursedPowers.CANT_SPRINT.isActive(this)) {
            ci.cancel();
        }
    }

    @Inject(method = "drop", at = @At("HEAD"))
    private void creeperDisc(DamageSource source, CallbackInfo ci) {
        if (!CursedPowers.THIS_IS_A_SECRET_TO_ALL.isActive(this) || this.world.isClient) return;
        if (source.getAttacker() instanceof SkeletonEntity) {
            this.dropItem(CursedItems.CREEPER_DISC);
        }
        if (source.getAttacker() instanceof PlayerEntity && source.getAttacker() != this && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            int lootingLevel = EnchantmentHelper.getLooting((LivingEntity) source.getAttacker());
            float f = (float)lootingLevel * this.world.random.nextFloat();
            for (int i = 0; i < this.world.random.nextInt(2) + Math.round(f); ++i) {
                this.dropItem(Items.GUNPOWDER);
            }
        }
    }
}
