package com.example.examplemod.entity;

import com.example.examplemod.ExampleMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CafeEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExampleMod.MODID);

    public static final RegistryObject<EntityType<SeatEntity>> SEAT = registerEntity("seat",
            EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC).sized(0.25f, 0.35f));
    public static final RegistryObject<EntityType<VillagerCustomer>> VILLAGER = registerEntity("villager",
            EntityType.Builder.of(VillagerCustomer::new, MobCategory.CREATURE).sized(0.6f, 1.8f));
    static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, () -> builder.build(ExampleMod.MODID + ":" + name));
    }

    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(VILLAGER.get(), Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 20.0).build());
    }

}
