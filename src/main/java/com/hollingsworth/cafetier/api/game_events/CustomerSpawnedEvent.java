package com.hollingsworth.cafetier.api.game_events;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;

public class CustomerSpawnedEvent implements IStateEvent {
    public Customer customer;

    public CustomerSpawnedEvent(Customer customer) {
        this.customer = customer;
    }
}
