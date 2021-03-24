package io.github.merchantpug.cursedorigins.registry;

import io.github.apace100.origins.power.*;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.HudRender;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.cursedorigins.CursedOrigins;
import io.github.merchantpug.cursedorigins.power.EatGrassPower;
import io.github.merchantpug.cursedorigins.power.MilkableMilkPower;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class CursedPowers {
    private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

    public static final PowerFactory<Power> EAT_GRASS = create(new PowerFactory<>(new Identifier(CursedOrigins.MODID, "eat_grass"),
            new SerializableData()
                    .add("cooldown", SerializableDataType.INT)
                    .add("hud_render", SerializableDataType.HUD_RENDER)
                    .add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            data ->
                    (type, player) -> {
            EatGrassPower power = new EatGrassPower(type, player, data.getInt("cooldown"), (HudRender)data.get("hud_render"));
            power.setKey((Active.Key)data.get("key"));
            return power;
        }).allowCondition());
    public static final PowerFactory<Power> MILKABLE_MILK = create(new PowerFactory<>(new Identifier(CursedOrigins.MODID, "milkable_milk"),
            new SerializableData()
                    .add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            data ->
                    (type, player) -> {
            MilkableMilkPower power = new MilkableMilkPower(type, player);
            power.setKey((Active.Key)data.get("key"));
            return power;
        }).allowCondition());
    public static final PowerType<Power> CALCIUM = new PowerTypeReference(new Identifier(CursedOrigins.MODID, "status_immunity"));
    public static final PowerType<Power> CAKE_IS_A_LIE = new PowerTypeReference(new Identifier(CursedOrigins.MODID, "cake_is_a_lie"));



    private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
        POWER_FACTORIES.put(factory, factory.getSerializerId());
        return factory;
    }

    public static void init() {
        POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ModRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
    }
}
