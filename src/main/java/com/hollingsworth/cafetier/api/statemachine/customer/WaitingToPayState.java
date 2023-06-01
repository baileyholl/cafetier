package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.game_events.CustomerPaidEvent;
import com.hollingsworth.cafetier.api.game_events.InteractEvent;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class WaitingToPayState implements IState {
    public Customer customer;
    public int ticksWaited = 0;

    public WaitingToPayState(Customer customer){
        this.customer = customer;
    }

    @Override
    public void onStart() {

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
        if(event instanceof InteractEvent event1){
            customer.cafe.postEvent(new CustomerPaidEvent(customer));
            return new LeavingCafeState(customer);
        }
        return null;
    }
}
