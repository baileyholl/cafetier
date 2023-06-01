package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class WaitingToOrderState implements IState {

    public final Customer customer;
    public int ticksWaited = 0;
    public int ticksToWait = 0;
    public WaitingToOrderState(Customer customer){
        this.customer = customer;
        ticksToWait = 60;
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
        ticksWaited++;
        if(ticksWaited >= ticksToWait){
            return new WaitingForFoodState(customer, customer.cafe.getGame().menuStacks.get(customer.getRandom().nextInt(customer.cafe.getGame().menuStacks.size())));
        }
        return null;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
