package io.github.merchantpug.cursedorigins;

import io.github.merchantpug.cursedorigins.networking.packet.EatGrassPacket;
import io.github.merchantpug.cursedorigins.networking.packet.MilkPacket;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class CursedOrigins implements ModInitializer {
	public static final String MODID = "cursedorigins";

	@Override
	public void onInitialize() {
		CursedPowers.init();
		ServerPlayNetworking.registerGlobalReceiver(EatGrassPacket.ID, EatGrassPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(MilkPacket.ID, MilkPacket::handle);
	}
}
