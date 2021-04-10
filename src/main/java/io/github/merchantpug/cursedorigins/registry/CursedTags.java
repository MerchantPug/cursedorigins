package io.github.merchantpug.cursedorigins.registry;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class CursedTags {
    public static final Tag<Item> LOW_TIER_AXES = TagRegistry.item(new Identifier(CursedOrigins.MODID, "low_tier_axes"));
    public static final Tag<Item> MID_TIER_AXES = TagRegistry.item(new Identifier(CursedOrigins.MODID, "mid_tier_axes"));
    public static final Tag<Item> HIGH_TIER_AXES = TagRegistry.item(new Identifier(CursedOrigins.MODID, "high_tier_axes"));
}
