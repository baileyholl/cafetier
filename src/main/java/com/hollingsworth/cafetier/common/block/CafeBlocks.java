package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.common.lib.BlockNames;
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

    public static final RegistryWrapper<CashRegister> CASH_REGISTER = registerBlock(BlockNames.CASH_REGISTER, () -> new CashRegister(defaultProperties().noOcclusion()));
    public static final RegistryWrapper<SeatBlock> OAK_CHAIR = registerBlock(BlockNames.OAK_CHAIR, () -> new SeatBlock(defaultProperties().noOcclusion()));
    public static final RegistryWrapper<ManagementDesk> MANAGEMENT_DESK = registerBlock(BlockNames.MANAGEMENT_DESK, () -> new ManagementDesk(defaultProperties().noOcclusion()));
    public static final RegistryWrapper<CustomerSpawner> CUSTOMER_SPAWNER = registerBlock(BlockNames.CUSTOMER_SPAWNER, () -> new CustomerSpawner(defaultProperties()));
    public static final RegistryWrapper<DisplayBlock> DISPLAY_BLOCK = registerBlock(BlockNames.DISPLAY_CASE, () -> new DisplayBlock(defaultProperties().noOcclusion()));
    public static final RegistryWrapper<PlateBlock> PLATE_BLOCK = registerBlock(BlockNames.PLATE_BLOCK, () -> new PlateBlock(defaultProperties().noOcclusion()));

    public static final RegistryObject<BlockEntityType<CashRegisterEntity>> CASH_REGISTER_ENTITY = BLOCK_ENTITIES.register(BlockNames.CASH_REGISTER, () -> BlockEntityType.Builder.of(CashRegisterEntity::new, CASH_REGISTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<ManagementDeskEntity>> MANAGEMENT_DESK_ENTITY = BLOCK_ENTITIES.register(BlockNames.MANAGEMENT_DESK, () -> BlockEntityType.Builder.of(ManagementDeskEntity::new, MANAGEMENT_DESK.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomerSpawnerEntity>> CUSTOMER_SPAWNER_ENTITY = BLOCK_ENTITIES.register(BlockNames.CUSTOMER_SPAWNER, () -> BlockEntityType.Builder.of(CustomerSpawnerEntity::new, CUSTOMER_SPAWNER.get()).build(null));
    public static final RegistryObject<BlockEntityType<DisplayEntity>> DISPLAY_ENTITY = BLOCK_ENTITIES.register(BlockNames.DISPLAY_CASE, () -> BlockEntityType.Builder.of(DisplayEntity::new, DISPLAY_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<PlateEntity>> PLATE_ENTITY = BLOCK_ENTITIES.register(BlockNames.PLATE_BLOCK, () -> BlockEntityType.Builder.of(PlateEntity::new, PLATE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockItem> CASH_REGISTER_ITEM = BLOCK_ITEMS.register(BlockNames.CASH_REGISTER, () -> new BlockItem(CASH_REGISTER.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static final RegistryObject<BlockItem> SEAT_ITEM = BLOCK_ITEMS.register(BlockNames.OAK_CHAIR, () -> new BlockItem(OAK_CHAIR.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static final RegistryObject<BlockItem> MANAGEMENT_DESK_ITEM = BLOCK_ITEMS.register(BlockNames.MANAGEMENT_DESK, () -> new BlockItem(MANAGEMENT_DESK.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static final RegistryObject<BlockItem> CUSTOMER_SPAWNER_ITEM = BLOCK_ITEMS.register(BlockNames.CUSTOMER_SPAWNER, () -> new BlockItem(CUSTOMER_SPAWNER.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static final RegistryObject<BlockItem> DISPLAY_BLOCK_ITEM = BLOCK_ITEMS.register(BlockNames.DISPLAY_CASE, () -> new BlockItem(DISPLAY_BLOCK.get(), new Item.Properties().tab(Cafetier.TAB)));
    public static final RegistryObject<BlockItem> PLATE_BLOCK_ITEM = BLOCK_ITEMS.register(BlockNames.PLATE_BLOCK, () -> new BlockItem(PLATE_BLOCK.get(), new Item.Properties().tab(Cafetier.TAB)));

    public static BlockBehaviour.Properties defaultProperties() {
        return BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0f, 6.0f);
    }

    static RegistryWrapper registerBlock(String name, Supplier<Block> blockSupp) {
        return new RegistryWrapper<>(BLOCK_REGISTRY.register(name, blockSupp));
    }
}
