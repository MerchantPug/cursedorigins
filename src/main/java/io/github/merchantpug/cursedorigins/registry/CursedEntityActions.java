package io.github.merchantpug.cursedorigins.registry;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.merchantpug.cursedorigins.action.LiteralCreeperExplodeAction;
import net.minecraft.entity.Entity;
import net.minecraft.util.registry.Registry;

public class CursedEntityActions {
    public static void register() {
        register(LiteralCreeperExplodeAction.getFactory());
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
