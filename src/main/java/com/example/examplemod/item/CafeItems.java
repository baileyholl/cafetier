package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.RegistryWrapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CafeItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);
    public static final RegistryWrapper<DeedItem> DEED = register("deed", () -> new DeedItem(defaultItemProperties()));
    public static Item.Properties defaultItemProperties() {
        return new Item.Properties().tab(ExampleMod.TAB);
    }

    public static RegistryWrapper register(String name, Supplier<? extends Item> item) {
        return new RegistryWrapper<>(ITEMS.register(name, item));
    }

}
