package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.common.entity.Customer;
import org.jetbrains.annotations.Nullable;

public abstract class CustomerPatienceState extends CustomerState{
    public int ticksWaiting = 0;

    public int maxWaitTime;

    public CustomerPatienceState(Customer customer, int maxWaitTime){
        super(customer);
        this.maxWaitTime = maxWaitTime;
    }

    @Nullable
    @Override
    public CustomerState tick() {
        ticksWaiting++;
        if(ticksWaiting >= getMaxWaitTime() && ticksWaiting % 20 == 0){
            customer.loseHappiness(getImpatiencePenalty());
        }
        return null;
    }

    public int getMaxWaitTime(){
        return maxWaitTime;
    }

    public int getImpatiencePenalty(){
        return 1;
    }

    public int getSecondsRemaining(){
        return Math.max(0, (maxWaitTime - ticksWaiting) / 20);
    }
}
