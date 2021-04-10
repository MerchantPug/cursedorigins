package io.github.merchantpug.cursedorigins.registry;

import net.minecraft.item.FoodComponent;

public class CursedFoodComponents {
    public static final FoodComponent GRASS = (new FoodComponent.Builder()).hunger(5).saturationModifier(6.0F).alwaysEdible().build();
    public static final FoodComponent TALL_GRASS = (new FoodComponent.Builder()).hunger(8).saturationModifier(12.8F).alwaysEdible().build();
}
