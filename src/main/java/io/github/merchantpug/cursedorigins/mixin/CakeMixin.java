package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CakeBlock.class)
public class CakeMixin {
    @Inject(method = "tryEat", at = @At("HEAD"), cancellable = true)
    private void tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<ActionResult> cir) {
        if (CursedPowers.CAKE_IS_A_LIE.isActive(player)) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
