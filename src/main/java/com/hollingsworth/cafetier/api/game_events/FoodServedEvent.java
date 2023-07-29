package com.hollingsworth.cafetier.api.game_events;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public class FoodServedEvent implements IStateEvent {

    public final BlockPos pos;

    public ItemStack food;
    public Customer claimedBy;

    public FoodServedEvent(BlockPos pos, ItemStack food) {
        this.pos = pos;
        this.food = food;
    }

    public void claim(Customer customer){
        this.claimedBy = customer;
    }

    public boolean isClaimed(){
        return claimedBy != null && claimedBy.getEatingAt() != null && claimedBy.getEatingAt().equals(pos);
    }
}
