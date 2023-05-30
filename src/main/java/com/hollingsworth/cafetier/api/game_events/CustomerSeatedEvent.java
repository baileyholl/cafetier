package com.hollingsworth.cafetier.api.game_events;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.core.BlockPos;

public class CustomerSeatedEvent implements IStateEvent {
    public final Customer customer;
    public final BlockPos seatPos;

    public CustomerSeatedEvent(Customer customer, BlockPos seatPos){
        this.customer = customer;
        this.seatPos = seatPos;
    }
}
