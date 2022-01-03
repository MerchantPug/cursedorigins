package io.github.merchantpug.cursedorigins.registry;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.merchantpug.cursedorigins.CursedOrigins;
import net.minecraft.util.Identifier;

public class CursedPowers {
    public static final PowerTypeReference<Power> CANT_SPRINT = new PowerTypeReference<>(new Identifier(CursedOrigins.MODID, "cant_sprint"));

    public static final PowerTypeReference<Power> GLASS = new PowerTypeReference<>(new Identifier(CursedOrigins.MODID, "glass"));
    public static final PowerTypeReference<Power> CANNON = new PowerTypeReference<>(new Identifier(CursedOrigins.MODID, "cannon"));
    public static final PowerTypeReference<Power> COMEDY = new PowerTypeReference<>(new Identifier(CursedOrigins.MODID, "comedy"));

    public static final PowerTypeReference<Power> THIS_IS_A_SECRET_TO_ALL = new PowerTypeReference<>(new Identifier(CursedOrigins.MODID, "this_is_a_secret_to_all"));
}
