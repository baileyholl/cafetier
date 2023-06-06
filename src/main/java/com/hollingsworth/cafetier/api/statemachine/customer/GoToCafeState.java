package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoToCafeState extends CustomerState{
    public BlockPos targetPos;
    public boolean enteredCafe;
    public int timeInCafe;
    public int timePathing;

    public GoToCafeState(Customer customer) {
        super(customer);
    }

    @Override
    public void onStart() {
        CafeGame game = customer.game;
        if(game == null){
            return;
        }
        if(!game.waitingPositions.isEmpty()){
            List<BlockPos> waitingPositions = game.waitingPositions;
            targetPos = waitingPositions.get(customer.getRandom().nextInt(waitingPositions.size()));
        }else{
            targetPos = game.getCafePos();
        }
    }

    @Override
    public void onEnd() {
        customer.getNavigation().stop();
    }

    @Nullable
    @Override
    public CustomerState tick() {
        Path path = customer.getNavigation().createPath(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1);
        customer.getNavigation().moveTo(path, 0.5);

        timePathing++;
        if(timePathing > 20 * 30){
            return new WaitForSeatingState(customer);
        }

        if(!enteredCafe && customer.game.cafe.getBounds().contains(customer.position())){
            enteredCafe = true;
        }
        if(enteredCafe){
            timeInCafe++;
            if(timeInCafe > 60){
                return new WaitForSeatingState(customer);
            }
        }
        if(BlockUtil.distanceBetween(targetPos, customer.blockPosition()) <= 2.5)
            return new WaitForSeatingState(customer);
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        return null;
    }
}
