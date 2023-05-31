package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class EatingState implements IState {

    public Customer customer;
    public int ticksEating;
    public int ticksToEat;

    public EatingState(Customer customer) {
        this.customer = customer;
        ticksToEat = 60;
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
        ticksEating++;
        if (ticksEating >= ticksToEat) {
            return new LeavingCafeState(customer);
        }
        return null;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
