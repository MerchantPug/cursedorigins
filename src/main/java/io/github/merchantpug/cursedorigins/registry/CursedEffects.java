package io.github.merchantpug.cursedorigins.registry;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import io.github.merchantpug.cursedorigins.effect.ChargedStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CursedEffects {
    public static final StatusEffect CHARGED = new ChargedStatusEffect();

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(CursedOrigins.MODID, "charged"), CHARGED);
    }
}
