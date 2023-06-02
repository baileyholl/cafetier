package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.entity.SeatEntity;
import com.hollingsworth.cafetier.common.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

public class LeavingCafeState extends CustomerState {

    public int ticksLeaving;
    public int maxTicksToLeave;

    public LeavingCafeState(Customer customer) {
        super(customer);
        maxTicksToLeave = 20 * 60;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Nullable
    @Override
    public CustomerState tick() {
        ticksLeaving++;
        if(ticksLeaving >= maxTicksToLeave){
            customer.remove(Entity.RemovalReason.DISCARDED);
            return null;
        }
        if(customer.isPassenger() && customer.getVehicle() instanceof SeatEntity seatEntity){
            customer.stopRiding();
        }
        BlockPos targetPos = customer.spawnPos;
        Path path = customer.getNavigation().createPath(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1);
        customer.getNavigation().moveTo(path, 0.5);
        if(BlockUtil.distanceBetween(targetPos, customer.blockPosition()) < 2){
            customer.remove(Entity.RemovalReason.DISCARDED);
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        return null;
    }
}
