package io.github.merchantpug.cursedorigins.registry;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CursedSounds {

    public static final SoundEvent MUSIC_DISC_CREEPER = register("music_disc.creeper");

    public static SoundEvent register(String name) {
        Identifier id = new Identifier(CursedOrigins.MODID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void init() {

    }
}
