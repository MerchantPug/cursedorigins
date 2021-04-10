package io.github.merchantpug.cursedorigins.registry;

import io.github.merchantpug.cursedorigins.CursedOrigins;
import io.github.merchantpug.cursedorigins.items.AbstractDiscItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class CursedItems {
    public static final Item CREEPER_DISC = new AbstractDiscItem(15, CursedSounds.MUSIC_DISC_CREEPER, (new Item.Settings()).rarity(Rarity.RARE).maxCount(1).group(ItemGroup.MISC));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(CursedOrigins.MODID, "music_disc_creeper_rap"), CREEPER_DISC);
    }
}
