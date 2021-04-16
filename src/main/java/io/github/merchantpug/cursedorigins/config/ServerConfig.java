package io.github.merchantpug.cursedorigins.config;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;

@Config(name = CursedOrigins.MODID + "_server")
public class ServerConfig implements ConfigData {

    public ArrayList<ConfigFoodItem> minotaur_food = new ArrayList<ConfigFoodItem>(Arrays.asList(
            new ConfigFoodItem("minecraft:grass", 5, 6.0F),
            new ConfigFoodItem("minecraft:seagrass", 5, 6.0F),
            new ConfigFoodItem("minecraft:fern", 5, 6.0F),
            new ConfigFoodItem("minecraft:tall_grass", 8, 12.8F),
            new ConfigFoodItem("minecraft:large_fern", 8, 12.8F),
            new ConfigFoodItem("minecraft:dead_bush", 1, -2.0F)
    ));
}
