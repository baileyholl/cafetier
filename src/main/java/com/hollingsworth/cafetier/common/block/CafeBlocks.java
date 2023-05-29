package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.Cafetier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CafeBlocks {
    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Cafetier.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Cafetier.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cafetier.MODID);

    public static final RegistryWrapper<CashRegister> CASH_REGISTER = registerBlock("cash_register", () -> new CashRegister(defaultProperties()));
    public static final RegistryWrapper<SeatBlock> SEAT = registerBlock("seat", () -> new SeatBlock(defaultProperties()));
    public static final RegistryWrapper<ManagementDesk> MANAGEMENT_DESK = registerBlock("management_desk", () -> new ManagementDesk(defaultProperties()));
    public static final RegistryWrapper<CustomerSpawner> CUSTOMER_SPAWNER = registerBlock("customer_spawner", () -> new CustomerSpawner(defaultProperties()));


    public static final RegistryObject<BlockEntityType<CashRegisterEntity>> CASH_REGISTER_ENTITY = BLOCK_ENTITIES.register("cash_register", () -> BlockEntityType.Builder.of(CashRegisterEntity::new, CASH_REGISTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<ManagementDeskEntity>> MANAGEMENT_DESK_ENTITY = BLOCK_ENTITIES.register("management_desk", () -> BlockEntityType.Builder.of(ManagementDeskEntity::new, MANAGEMENT_DESK.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomerSpawnerEntity>> CUSTOMER_SPAWNER_ENTITY = BLOCK_ENTITIES.register("customer_spawner", () -> BlockEntityType.Builder.of(CustomerSpawnerEntity::new, CUSTOMER_SPAWNER.get()).build(null));

    public static final RegistryObject<BlockItem> CASH_REGISTER_ITEM = BLOCK_ITEMS.register("cash_register", () -> new BlockItem(CASH_REGISTER.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static final RegistryObject<BlockItem> SEAT_ITEM = BLOCK_ITEMS.register("seat", () -> new BlockItem(SEAT.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static final RegistryObject<BlockItem> MANAGEMENT_DESK_ITEM = BLOCK_ITEMS.register("management_desk", () -> new BlockItem(MANAGEMENT_DESK.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static final RegistryObject<BlockItem> CUSTOMER_SPAWNER_ITEM = BLOCK_ITEMS.register("customer_spawner", () -> new BlockItem(CUSTOMER_SPAWNER.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static BlockBehaviour.Properties defaultProperties() {
        return BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0f, 6.0f);
    }

    static RegistryWrapper registerBlock(String name, Supplier<Block> blockSupp) {
        return new RegistryWrapper<>(BLOCK_REGISTRY.register(name, blockSupp));
    }
}
