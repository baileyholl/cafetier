package com.hollingsworth.cafetier.api.game_events;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;

public class CustomerPaidEvent implements IStateEvent {

    public Customer customer;
    public CustomerPaidEvent(Customer customer){
        this.customer = customer;
    }
}
