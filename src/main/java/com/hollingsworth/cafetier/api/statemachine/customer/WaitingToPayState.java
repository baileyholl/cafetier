package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.game_events.CustomerPaidEvent;
import com.hollingsworth.cafetier.api.game_events.InteractEvent;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class WaitingToPayState extends CustomerState {

    public int ticksWaited = 0;
    public int maxWait;

    public WaitingToPayState(Customer customer){
        super(customer);
        maxWait = customer.maxWaitToPay();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Nullable
    @Override
    public CustomerState tick() {
        ticksWaited++;
        if(ticksWaited >= maxWait && ticksWaited % 20 == 0){
            customer.loseHappiness(1);
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        if(event instanceof InteractEvent event1){
            customer.cafe.postEvent(new CustomerPaidEvent(customer));
            return new LeavingCafeState(customer);
        }
        return null;
    }
}
