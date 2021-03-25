package io.github.merchantpug.cursedorigins.networking.packet;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class MilkPacket {
    public static final Identifier ID = new Identifier(CursedOrigins.MODID, "milk_milk");

    public static void send() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        server.execute(() -> {
            Hand hand = player.getActiveHand();
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.getItem() == Items.BUCKET) {
                ItemStack replaceWith = ItemUsage.method_30012(itemStack, player, Items.MILK_BUCKET.getDefaultStack());
                player.setStackInHand(hand, replaceWith);
                player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_COW_MILK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        });
    }
}
