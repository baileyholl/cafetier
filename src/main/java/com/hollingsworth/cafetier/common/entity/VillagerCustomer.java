package com.hollingsworth.cafetier.common.entity;

import com.hollingsworth.cafetier.api.Cafe;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.Level;

public class VillagerCustomer extends Customer implements VillagerDataHolder {
    public VillagerCustomer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public VillagerCustomer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel, double x, double y, double z, Cafe cafe) {
        super(pEntityType, pLevel, x, y, z, cafe);
    }

    public VillagerCustomer(Level level, Cafe cafe){
        super(CafeEntities.VILLAGER.get(), level, cafe);
    }

    public VillagerCustomer(Level level, double x, double y, double z, Cafe cafe) {
        super(CafeEntities.VILLAGER.get(), level, x, y, z, cafe);
    }

    @Override
    public VillagerData getVillagerData() {
        return new VillagerData(VillagerType.DESERT, VillagerProfession.ARMORER, 1);
    }

    @Override
    public void setVillagerData(VillagerData pData) {

    }
}
