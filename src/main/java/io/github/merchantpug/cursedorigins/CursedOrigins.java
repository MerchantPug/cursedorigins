package io.github.merchantpug.cursedorigins;

import io.github.merchantpug.cursedorigins.registry.CursedEffects;
import io.github.merchantpug.cursedorigins.registry.CursedEntityActions;
import io.github.merchantpug.cursedorigins.registry.CursedItems;
import io.github.merchantpug.cursedorigins.registry.CursedSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CursedOrigins implements ModInitializer {
	public static final String MODID = "cursedorigins";
	public static final Logger LOGGER = LogManager.getLogger(CursedOrigins.class);

	@Override
	public void onInitialize() {
		LOGGER.info("CursedOrigins is initializing. Please laugh, I'm begging you!");

		CursedEffects.register();
		CursedEntityActions.register();
		CursedItems.register();
		CursedSounds.init();
	}

	public static Identifier identifier(String path) {
		return new Identifier("cursedorigins", path);
	}
}
