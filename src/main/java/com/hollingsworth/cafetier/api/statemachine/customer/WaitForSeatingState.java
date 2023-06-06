package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.api.game_events.CustomerSeatedEvent;
import com.hollingsworth.cafetier.api.game_events.InteractEvent;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public class WaitForSeatingState extends CustomerState {

    public int ticksWaiting = 0;

    public WaitForSeatingState(Customer customer) {
        super(customer);
    }

    @Override
    public void onStart() {
        customer.setCanBeSeated(true);
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
        ticksWaiting++;
        if(ticksWaiting >= customer.maxWaitForSeat() && ticksWaiting % 20 == 0){
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
            customer.acknowledgeServer(customerSeatedEvent.seatedBy);
            return new ReadingMenuState(customer);
        }
        return null;
    }
}
