package io.github.merchantpug.cursedorigins.mixin;

import com.google.common.collect.Multimap;
import io.github.merchantpug.cursedorigins.registry.CursedPowers;
import io.github.merchantpug.cursedorigins.registry.CursedTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    private EntityAttributeModifier entityAttributeModifier;

    @Shadow public abstract Item getItem();

    @Environment(EnvType.CLIENT)
    @Inject(method = "getTooltip", at = @At(value = "INVOKE", target = "net/minecraft/entity/attribute/EntityAttributeModifier.getValue()D"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getLocalsFromTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir,
                                      List list, int i, EquipmentSlot var6[], int var7, int var8, EquipmentSlot equipmentSlot, Multimap multimap, Iterator var11, Map.Entry<EntityAttribute, EntityAttributeModifier> entry,
                                      EntityAttributeModifier entityAttributeModifier) {
        this.entityAttributeModifier = entityAttributeModifier;
    }

    @Environment(EnvType.CLIENT)
    @ModifyVariable(method = "getTooltip", at = @At(value = "STORE", target = "net/minecraft/entity/attribute/EntityAttributeModifier.getValue()D"))
    private double modifyD(double d) {
        assert MinecraftClient.getInstance().player != null;
        if (CursedPowers.AXE_PROFICIENCY.get(MinecraftClient.getInstance().player) != null) {
            if (getItem() instanceof AxeItem) {
                if (entityAttributeModifier.getId().equals(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"))) {
                    double g = 0.0D;
                    if (CursedTags.LOW_TIER_AXES.contains(getItem())) {
                        g = 2.0D;
                    } else if (CursedTags.MID_TIER_AXES.contains(getItem())) {
                        g = 3.0D;
                    } else if (CursedTags.HIGH_TIER_AXES.contains(getItem())) {
                        g = 4.0D;
                    }
                    double h = g / 3;
                    return d += h;
                }
            }
        }
        return d;
    }
}
