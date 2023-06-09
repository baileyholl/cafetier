package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.game_events.CustomerDiedEvent;
import com.hollingsworth.cafetier.api.game_events.CustomerPaidEvent;
import com.hollingsworth.cafetier.api.game_events.CustomerSpawnedEvent;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    public List<Customer> trackedCustomers = new ArrayList<>();

    public void tick(){
        List<Customer> toRemove = new ArrayList<>();
        for(Customer customer : trackedCustomers){
            if(customer.isDeadOrDying() || customer.isRemoved()){
                toRemove.add(customer);
            }
        }
        trackedCustomers.removeAll(toRemove);
    }

    public void onEvent(IStateEvent event){
        if(event instanceof CustomerSpawnedEvent customerSpawnedEvent){
            trackedCustomers.add(customerSpawnedEvent.customer);
        }else if(event instanceof CustomerDiedEvent customerDiedEvent){
            trackedCustomers.remove(customerDiedEvent.customer);
        }else if(event instanceof CustomerPaidEvent customerPaidEvent){
            trackedCustomers.remove(customerPaidEvent.customer);
        }
        for(Customer customer : trackedCustomers){
            customer.onEvent(event);
        }
    }
}
