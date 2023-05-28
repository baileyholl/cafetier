package com.hollingsworth.cafetier.entity;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class Customer extends PathfinderMob {
    protected Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void onEvent(IStateEvent gameEvent){}

}
