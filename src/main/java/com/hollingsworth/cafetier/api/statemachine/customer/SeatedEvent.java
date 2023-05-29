package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.entity.Customer;
import net.minecraft.core.BlockPos;

public class SeatedEvent implements IStateEvent {
    public BlockPos seatPos;

    public Customer seatedCustomer;

    public SeatedEvent(BlockPos seatPos, Customer seatedCustomer) {
        this.seatPos = seatPos;
        this.seatedCustomer = seatedCustomer;
    }

}
