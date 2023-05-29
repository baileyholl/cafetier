package com.hollingsworth.cafetier.entity;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.statemachine.EmptyState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.statemachine.SimpleStateMachine;
import com.hollingsworth.cafetier.api.statemachine.customer.GoToCafeGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class Customer extends PathfinderMob {
    public Cafe targetCafe;

    public SimpleStateMachine brain = new SimpleStateMachine(EmptyState.INSTANCE);

    public Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel, double x, double y, double z, Cafe cafe){
        this(pEntityType, pLevel);
        this.setPos(x, y, z);
        this.targetCafe = cafe;
        brain = new SimpleStateMachine(new GoToCafeGoal(this));
    }

    public Customer(EntityType<? extends PathfinderMob> entityType, Level level, BlockPos spawnPos, Cafe cafe) {
        this(entityType, level, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, cafe);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide) {
            brain.tick();
        }
    }

    public void onEvent(IStateEvent gameEvent){}

}
