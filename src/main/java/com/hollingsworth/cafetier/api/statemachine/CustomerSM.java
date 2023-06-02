package com.hollingsworth.cafetier.api.statemachine;

import com.hollingsworth.cafetier.api.statemachine.customer.CustomerState;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CustomerSM extends SimpleStateMachine<CustomerState, IStateEvent> {
    public Customer customer;

    public CustomerSM(@NotNull CustomerState initialState, Customer customer) {
        super(initialState);
        this.customer = customer;
        if(DEBUG){
            customer.setCustomName(Component.literal(currentState.getClass().getSimpleName()));
        }
    }

    @Override
    protected void changeState(@NotNull CustomerState nextState) {
        super.changeState(nextState);
        if(currentState != null && DEBUG){
            customer.setCustomName(Component.literal(currentState.getClass().getSimpleName()));
        }
    }

    public void setState(@NotNull CustomerState state){
        this.changeState(state);
    }
}
