package io.github.merchantpug.cursedorigins;

import io.github.merchantpug.cursedorigins.config.MinotaurConfig;
import io.github.merchantpug.cursedorigins.networking.packet.EatGrassPacket;
import io.github.merchantpug.cursedorigins.networking.packet.MilkPacket;
import io.github.merchantpug.cursedorigins.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CursedOrigins implements ModInitializer {
	public static final String MODID = "cursedorigins";
	public static final Logger LOGGER = LogManager.getLogger(CursedOrigins.class);
	public static boolean configRegistered = false;

	@Override
	public void onInitialize() {
		LOGGER.info("CursedOrigins is initializing. Please laugh, I'm begging you!");

		if(!configRegistered) {
			AutoConfig.register(MinotaurConfig.class, Toml4jConfigSerializer::new);
			configRegistered = true;
		}

		CursedConditions.register();
		CursedEffects.register();
		CursedItems.register();
		CursedPowers.init();
		CursedSounds.init();
		ServerPlayNetworking.registerGlobalReceiver(EatGrassPacket.ID, EatGrassPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(MilkPacket.ID, MilkPacket::handle);
	}
}
