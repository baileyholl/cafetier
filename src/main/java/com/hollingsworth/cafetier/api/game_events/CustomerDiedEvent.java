package com.hollingsworth.cafetier.api.game_events;

import com.hollingsworth.cafetier.common.entity.Customer;

public class CustomerDiedEvent extends CustomerEvent {

    public CustomerDiedEvent(Customer customer) {
        super(customer);
    }
}
