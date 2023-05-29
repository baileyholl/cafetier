package com.hollingsworth.cafetier.common.item;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.common.block.RegistryWrapper;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CafeItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cafetier.MODID);
    public static final RegistryWrapper<DeedItem> DEED = register("deed", () -> new DeedItem(defaultItemProperties()));
    public static Item.Properties defaultItemProperties() {
        return new Item.Properties().tab(Cafetier.TAB);
    }

    public static RegistryWrapper register(String name, Supplier<? extends Item> item) {
        return new RegistryWrapper<>(ITEMS.register(name, item));
    }

}
