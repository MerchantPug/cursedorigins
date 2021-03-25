package io.github.merchantpug.cursedorigins.power;

import io.github.apace100.origins.power.Active;
import io.github.merchantpug.cursedorigins.networking.packet.MilkPacket;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public class MilkableMilkPower extends Power implements Active {
    private Key key;

    public MilkableMilkPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }

    @Override
    public void onUse() {
        if (player.world.isClient) {
            MilkPacket.send();
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
