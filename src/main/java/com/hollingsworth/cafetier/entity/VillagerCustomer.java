package com.hollingsworth.cafetier.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.Level;

public class VillagerCustomer extends Customer implements VillagerDataHolder {
    protected VillagerCustomer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public VillagerData getVillagerData() {
        return new VillagerData(VillagerType.DESERT, VillagerProfession.ARMORER, 1);
    }

    @Override
    public void setVillagerData(VillagerData pData) {

    }
}
