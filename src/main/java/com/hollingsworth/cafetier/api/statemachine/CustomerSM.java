package com.hollingsworth.cafetier.api.statemachine;

import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CustomerSM extends SimpleStateMachine{
    Customer customer;
    public CustomerSM(@NotNull IState initialState, Customer customer) {
        super(initialState);
        this.customer = customer;
        if(DEBUG){
            customer.setCustomName(Component.literal(currentState.getClass().getSimpleName()));
        }
    }

    @Override
    protected void changeState(@NotNull IState nextState) {
        super.changeState(nextState);
        if(currentState != null && DEBUG){
            customer.setCustomName(Component.literal(currentState.getClass().getSimpleName()));
        }
    }
}
