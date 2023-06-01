package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WaitingForFoodState implements IState {
    public Customer customer;

    public WaitingForFoodState(Customer customer, ItemStack desiredStack) {
        this.customer = customer;
        customer.setDesiredItem(desiredStack.copy());
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {
        customer.setDesiredItem(ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public IState tick() {
        return null;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        if(event instanceof InteractEvent event1 && event1.player.getItemInHand(((InteractEvent) event).hand).sameItem(customer.getDesiredItem())){
            return new EatingState(customer);
        }
        return null;
    }
}
