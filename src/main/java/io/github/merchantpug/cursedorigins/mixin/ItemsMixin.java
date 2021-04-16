package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import io.github.merchantpug.cursedorigins.config.ConfigFoodItem;
import io.github.merchantpug.cursedorigins.config.ServerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(at = @At("HEAD"), method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;")
    private static void modifyRegister(Identifier id, Item item, CallbackInfoReturnable<Item> cir) {
        if(!CursedOrigins.configRegistered) {
            AutoConfig.register(ServerConfig.class, Toml4jConfigSerializer::new);
            CursedOrigins.configRegistered = true;
        }
        ServerConfig config = AutoConfig.getConfigHolder(ServerConfig.class).getConfig();
        for (int i = 0; i < config.minotaur_food.size(); i++) {
            ConfigFoodItem currentItem = config.minotaur_food.get(i);
            if (id.equals(new Identifier(currentItem.itemId))) {
                ((ItemAccessor) item).setFoodComponent(new FoodComponent.Builder()
                        .hunger(currentItem.hungerShanks)
                        .saturationModifier(currentItem.saturation)
                        .build());
            }
        }
    }
}
