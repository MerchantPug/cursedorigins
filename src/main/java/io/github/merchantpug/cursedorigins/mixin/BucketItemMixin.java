package io.github.merchantpug.cursedorigins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.merchantpug.cursedorigins.networking.packet.MilkPacket;
import io.github.merchantpug.cursedorigins.power.MilkableMilkPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BucketItem.class)
public class BucketItemMixin extends Item {
    public BucketItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (entity instanceof PlayerEntity && OriginComponent.hasPower(entity, MilkableMilkPower.class)) {
            if (stack.getItem() == Items.BUCKET) {
                if (player.world.isClient) {
                    MilkPacket.send();
                }
                ((PlayerEntity) entity).addExhaustion(10);
                return ActionResult.success(player.world.isClient);
            } else {
                return ActionResult.PASS;
            }
        } else {
            return ActionResult.PASS;
        }
    }
}
