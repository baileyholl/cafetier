package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WaitingToOrderState extends CustomerState {

    public int ticksWaited = 0;
    public int ticksToWait;
    public ItemStack desiredStack;

    public WaitingToOrderState(Customer customer){
        super(customer);
        ticksToWait = customer.timeToOrder();
        desiredStack = getGame().menuStacks.get(customer.getRandom().nextInt(customer.cafe.getGame().menuStacks.size()));
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
        if(customer.getSeatedPos() == null){
            return new NeedsReseatedState(customer, this);
        }
        ticksWaited++;
        if(ticksWaited >= ticksToWait){
            customer.setDesiredItem(desiredStack);
            return new WaitingForFoodState(customer, desiredStack);
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        return null;
    }
}
