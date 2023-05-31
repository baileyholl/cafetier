package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.game_events.CustomerSeatedEvent;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class WaitForSeatingState implements IState {

    public Customer customer;

    public WaitForSeatingState(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void onStart() {
        customer.setCanBeSeated(true);
    }

    @Override
    public void onEnd() {

    }

    @Nullable
    @Override
    public IState tick() {
        return null;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        if(event instanceof InteractEvent interactEvent && customer.getLeashHolder() != interactEvent.player){
            customer.setLeashedTo(interactEvent.player, true);
        }
        if(event instanceof CustomerSeatedEvent customerSeatedEvent && customerSeatedEvent.customer == customer){
            customer.setCanBeSeated(false);
            return new WaitingToOrderState(customer);
        }
        return null;
    }
}
