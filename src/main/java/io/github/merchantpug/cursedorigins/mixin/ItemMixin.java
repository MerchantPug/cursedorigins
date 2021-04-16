package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import io.github.merchantpug.cursedorigins.config.ConfigFoodItem;
import io.github.merchantpug.cursedorigins.config.ServerConfig;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemConvertible {

    @Shadow public abstract boolean isFood();

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    private void preventEatingFood(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (this.isFood()) {
            if (!CursedOrigins.configRegistered) {
                AutoConfig.register(ServerConfig.class, Toml4jConfigSerializer::new);
                CursedOrigins.configRegistered = true;
            }
            ServerConfig config = AutoConfig.getConfigHolder(ServerConfig.class).getConfig();
            ItemStack stackInHand = user.getStackInHand(hand);
            for (int i = 0; i < config.minotaur_food.size(); i++) {
                ConfigFoodItem currentItem = config.minotaur_food.get(i);
                if (currentItem.itemId.equals(Registry.ITEM.getId(stackInHand.getItem()).toString())) {

                    if (!CursedPowers.INVENTORY_EAT_GRASS.isActive(user)) {
                        cir.setReturnValue(TypedActionResult.fail(stackInHand));
                    }
                }
            }
        }
    }
}
