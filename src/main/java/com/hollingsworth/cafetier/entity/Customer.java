package com.hollingsworth.cafetier.entity;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class Customer extends PathfinderMob {
    public Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel, double x, double y, double z) {
        super(pEntityType, pLevel);
        this.setPos(x, y, z);
    }

    public void onEvent(IStateEvent gameEvent){}

}
