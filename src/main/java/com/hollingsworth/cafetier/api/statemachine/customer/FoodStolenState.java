package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FoodStolenState extends WaitingForFoodState {

    public FoodStolenState(Customer customer, ItemStack desiredStack) {
        super(customer, desiredStack);
    }

    @Override
    public @Nullable CustomerState tick() {
        super.tick();
        if(getLevel().getGameTime() % 20 == 0){
            customer.loseHappiness(1);
        }
        return null;
    }
}
