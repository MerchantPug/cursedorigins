package io.github.merchantpug.cursedorigins;

import io.github.apace100.apoli.util.NamespaceAlias;
import io.github.merchantpug.cursedorigins.registry.CursedEffects;
import io.github.merchantpug.cursedorigins.registry.CursedEntityActions;
import io.github.merchantpug.cursedorigins.registry.CursedItems;
import io.github.merchantpug.cursedorigins.registry.CursedSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CursedOrigins implements ModInitializer {
	public static final String MODID = "cursedorigins";
	public static final Logger LOGGER = LogManager.getLogger(CursedOrigins.class);
	public static String VERSION = "";

	@Override
	public void onInitialize() {
		FabricLoader.getInstance().getModContainer(MODID).ifPresent(modContainer -> {
			VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
			if(VERSION.contains("+")) {
				VERSION = VERSION.split("\\+")[0];
			}
			if(VERSION.contains("-")) {
				VERSION = VERSION.split("-")[0];
			}
		});

		LOGGER.info("CursedOrigins " + VERSION + " is initializing. Please laugh, I'm begging you!");

		CursedEffects.register();
		CursedEntityActions.register();
		CursedItems.register();
		CursedSounds.init();

		NamespaceAlias.addAlias(MODID, "apugli");
	}

	public static Identifier identifier(String path) {
		return new Identifier(MODID, path);
	}
}
