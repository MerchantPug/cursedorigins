package io.github.merchantpug.cursedorigins.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import io.github.apace100.origins.Origins;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.registry.ModComponents;
import io.github.merchantpug.cursedorigins.CursedOrigins;
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
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    Text text = new TranslatableText("chat.creeperalert");
    private JsonObject layerObject;
    private JsonObject originObject;
    @Shadow public abstract void sendMessage(Text message, boolean actionBar);
    @Shadow public abstract ItemEntity dropItem(ItemStack stack, boolean retainOwnership);

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

    @Inject(method = "damageArmor", at = @At("HEAD"), cancellable = true)
    private void damageArmor(DamageSource source, float amount, CallbackInfo ci) {
        if (CursedPowers.GLASS.isActive(this)) {
            ci.cancel();
        }
    }

    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    private void changeToLiteralCreeper(DamageSource source, float amount, CallbackInfo ci) {
        if (!this.world.isClient) {
            if (!this.isInvulnerableTo(source)) {
                if (source.getName().equals("creeperExplosion.player") || source.getName().equals("chargedCreeperExplosion.player.item") || source.getName().equals("chargedCreeperExplosion.player") || source.getName().equals("chargedCreeperExplosion.player.item")) {
                    MinecraftServer server = this.getServer();
                    assert server != null;
                    ResourceManager manager = ((MinecraftServerAccessor)server).getServerResourceManager().getResourceManager();
                    try(InputStream layerInput = manager.getResource(new Identifier(Origins.MODID, "origin_layers/origin.json")).getInputStream()) {
                        JsonReader layerReader = new JsonReader(new InputStreamReader(layerInput));
                        this.layerObject = new JsonParser().parse(layerReader).getAsJsonObject();

                    } catch (IOException e) {
                        throw new RuntimeException("Failed to load OriginLayer file", e);
                    }
                    try(InputStream originInput = manager.getResource(new Identifier(CursedOrigins.MODID, "origins/literal_creeper.json")).getInputStream()) {
                        JsonReader originReader = new JsonReader(new InputStreamReader(originInput));
                        this.originObject = new JsonParser().parse(originReader).getAsJsonObject();
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to load Origin file", e);
                    }
                    if (!ModComponents.ORIGIN.get(this).getOrigin(OriginLayer.fromJson(new Identifier(Origins.MODID, "origin"), layerObject)).equals(Origin.fromJson(new Identifier(CursedOrigins.MODID, "literal_creeper"), originObject))) {
                        ModComponents.ORIGIN.get(this).setOrigin(OriginLayer.fromJson(new Identifier(Origins.MODID, "origin"), layerObject), Origin.fromJson(new Identifier(CursedOrigins.MODID, "literal_creeper"), originObject));
                        OriginComponent.sync((PlayerEntity) (Object) this);
                        this.sendMessage(text, false);
                    }
                }
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
