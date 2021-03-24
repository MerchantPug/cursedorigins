package io.github.merchantpug.cursedorigins.power;

import io.github.apace100.origins.power.ActiveCooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import io.github.merchantpug.cursedorigins.networking.packet.EatGrassPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class EatGrassPower extends ActiveCooldownPower {
    private Key key;

    public EatGrassPower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender) {
        super(type, player, cooldownDuration, hudRender, null);
    }

    @Override
    public void onUse() {
        if (this.canUse()) {
            if (player.world.isClient) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    EatGrassPacket.send(((BlockHitResult) client.crosshairTarget).getBlockPos());
                    this.use();
                }
            }
        }
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }
}
