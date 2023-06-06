package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class ReadingMenuState extends CustomerState{

    public int ticksWaited = 0;
    public int ticksToWait;
    public ReadingMenuState(Customer customer) {
        super(customer);
        ticksToWait = customer.timeToReadMenu();
    }

    @Override
    public void onStart() {
        customer.setDisplayIcon(Cafetier.MODID + ":textures/gui/icons/deciding_on_food.png");
    }

    @Override
    public void onEnd() {
        customer.setDisplayIcon("");
    }

    @Nullable
    @Override
    public CustomerState tick() {
        if(customer.getSeatedPos() == null){
            return new NeedsReseatedState(customer, this);
        }
        ticksWaited++;
        if(ticksWaited >= ticksToWait){
            return new WaitingToOrderState(customer);
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        return null;
    }
}
