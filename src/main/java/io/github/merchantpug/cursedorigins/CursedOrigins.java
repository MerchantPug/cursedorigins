package io.github.merchantpug.cursedorigins;

import io.github.merchantpug.cursedorigins.networking.packet.EatGrassPacket;
import io.github.merchantpug.cursedorigins.networking.packet.MilkPacket;
import io.github.merchantpug.cursedorigins.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CursedOrigins implements ModInitializer {
	public static final String MODID = "cursedorigins";
	public static String VERSION = "";
	public static int[] SEMVER;
	public static final Logger LOGGER = LogManager.getLogger(CursedOrigins.class);

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
			String[] splitVersion = VERSION.split("\\.");
			SEMVER = new int[splitVersion.length];
			for(int i = 0; i < SEMVER.length; i++) {
				SEMVER[i] = Integer.parseInt(splitVersion[i]);
			}
		});
		LOGGER.info("CursedOrigins " + VERSION + " is initializing. Please laugh, I'm begging you!");

		CursedConditions.register();
		CursedEffects.register();
		CursedItems.register();
		CursedPowers.init();
		CursedSounds.init();
		ServerPlayNetworking.registerGlobalReceiver(EatGrassPacket.ID, EatGrassPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(MilkPacket.ID, MilkPacket::handle);
	}
}
