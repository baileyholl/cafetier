package com.hollingsworth.cafetier.api.game_events;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.core.BlockPos;

public class FoodServedEvent implements IStateEvent {

    public final BlockPos pos;

    public Customer claimedBy;

    public FoodServedEvent(BlockPos pos) {
        this.pos = pos;
    }

    public void claim(Customer customer){
        this.claimedBy = customer;
    }

    public boolean isClaimed(){
        return claimedBy != null && claimedBy.getEatingAt() != null && claimedBy.getEatingAt().equals(pos);
    }
}
