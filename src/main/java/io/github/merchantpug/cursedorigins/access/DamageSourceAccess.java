package io.github.merchantpug.cursedorigins.access;

import net.minecraft.entity.damage.DamageSource;

public interface DamageSourceAccess {
    boolean isGlassGolemSource();
    DamageSource setGlassGolemSource();
}
