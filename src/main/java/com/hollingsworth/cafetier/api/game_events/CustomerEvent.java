package com.hollingsworth.cafetier.api.game_events;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;

public class CustomerEvent implements IStateEvent {
    public Customer customer;

    public CustomerEvent(Customer customer) {
        this.customer = customer;
    }
}
