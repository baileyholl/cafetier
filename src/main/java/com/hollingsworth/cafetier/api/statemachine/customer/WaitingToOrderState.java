package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class WaitingToOrderState extends CustomerState {

    public int ticksWaited = 0;
    public int ticksToWait;

    public WaitingToOrderState(Customer customer){
        super(customer);
        ticksToWait = customer.timeToOrder();
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
        if(ticksWaited >= ticksToWait){
            return new WaitingForFoodState(customer, customer.cafe.getGame().menuStacks.get(customer.getRandom().nextInt(customer.cafe.getGame().menuStacks.size())));
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        return null;
    }
}
