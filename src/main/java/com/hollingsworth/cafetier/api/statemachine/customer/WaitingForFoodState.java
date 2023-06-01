package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.game_events.FoodServedEvent;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.block.PlateEntity;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
//        if(event instanceof InteractEvent event1 && event1.player.getItemInHand(((InteractEvent) event).hand).sameItem(customer.getDesiredItem())){
//            return new EatingState(customer);
//        }
        if(event instanceof FoodServedEvent servedEvent){
            BlockPos seatPos = this.customer.getSeatedPos();
            if (seatPos != null
                    && customer.getLevel().getBlockEntity(servedEvent.pos) instanceof PlateEntity plateEntity
                    && plateEntity.getStack().sameItem(customer.getDesiredItem())) {

                // Check if the food was served 1 block up and is N/S/E/W of the seat
                for (Direction direction : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
                    BlockPos possiblePlate = seatPos.above().relative(direction);
                    if (servedEvent.pos.equals(possiblePlate)) {
                        servedEvent.claim(customer);
                        customer.setEatingAt(servedEvent.pos);
                        return new EatingState(customer, plateEntity.getStack().copy(), servedEvent.pos);
                    }
                }
            }
        }
        return null;
    }
}
