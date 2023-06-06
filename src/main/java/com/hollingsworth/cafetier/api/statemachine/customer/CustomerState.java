package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public abstract class CustomerState implements IState<CustomerState> {
    public Customer customer;

    public CustomerState(Customer customer){
        this.customer = customer;
    }

    public RandomSource getRandom(){
        return customer.getRandom();
    }

    public Level getLevel(){
        return customer.level;
    }

    public CafeGame getGame(){
        return customer.game;
    }
}
