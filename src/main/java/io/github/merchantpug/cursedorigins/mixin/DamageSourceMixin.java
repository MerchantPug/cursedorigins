package io.github.merchantpug.cursedorigins.mixin;

import io.github.merchantpug.cursedorigins.access.DamageSourceAccess;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DamageSource.class)
public class DamageSourceMixin implements DamageSourceAccess {
    @Unique
    private boolean isGlassGolem;

    @Override
    public boolean isGlassGolemSource() {
        return isGlassGolem;
    }

    @Override
    public DamageSource setGlassGolemSource() {
        this.isGlassGolem = true;
        return (DamageSource)(Object)this;
    }
}
