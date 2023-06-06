package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.api.game_events.CustomerSeatedEvent;
import com.hollingsworth.cafetier.api.game_events.InteractEvent;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class NeedsReseatedState extends CustomerState{
    public int ticks = 0;
    public CustomerState previousState;
    public NeedsReseatedState(Customer customer, CustomerState previousState){
        super(customer);
        this.previousState = previousState;
    }

    @Override
    public void onStart() {
        customer.setCanBeSeated(true);
        customer.loseHappiness(5);
        customer.setDisplayIcon(Cafetier.MODID + ":textures/gui/icons/waiting_for_seat.png");
    }

    @Override
    public void onEnd() {
        customer.setCanBeSeated(false);
        customer.setDisplayIcon("");
    }

    @Nullable
    @Override
    public CustomerState tick() {
        ticks++;
        if(ticks % 20 == 0){
            customer.loseHappiness(1);
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        if(event instanceof InteractEvent interactEvent && customer.getLeashHolder() != interactEvent.player){
            customer.setLeashedTo(interactEvent.player, true);
        }
        if(event instanceof CustomerSeatedEvent customerSeatedEvent && customerSeatedEvent.customer == customer){
            return previousState;
        }
        return null;
    }
}
