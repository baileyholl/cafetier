package com.hollingsworth.cafetier.api.game_events;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class CustomerSeatedEvent implements IStateEvent {
    public final Customer customer;
    public final BlockPos seatPos;
    public final @Nullable LivingEntity seatedBy;

    public CustomerSeatedEvent(Customer customer, BlockPos seatPos, LivingEntity seatedBy){
        this.customer = customer;
        this.seatPos = seatPos;
        this.seatedBy = seatedBy;
    }
}
