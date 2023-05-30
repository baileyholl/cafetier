package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

public class GoToCafeState implements IState {
    public Customer customer;
    public BlockPos targetPos;

    public GoToCafeState(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void onStart() {
        targetPos = customer.cafe.deskPos;
    }

    @Override
    public void onEnd() {
        customer.getNavigation().stop();
    }

    @Nullable
    @Override
    public IState tick() {
        Path path = customer.getNavigation().createPath(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1);
        customer.getNavigation().moveTo(path, 0.5);
        if(BlockUtil.distanceBetween(targetPos, customer.blockPosition()) < 2)
            return new WaitForSeatingState(customer);
        return null;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
