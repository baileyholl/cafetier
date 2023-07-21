package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.game_events.FoodServedEvent;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.block.PlateEntity;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WaitingForFoodState extends CustomerPatienceState {

    public WaitingForFoodState(Customer customer, ItemStack desiredStack) {
        super(customer, customer.maxWaitForFood());
        customer.setDesiredItem(desiredStack.copy());
    }

    @Override
    public void onStart() {
        customer.setShowDesiredItem(true);
    }

    @Override
    public void onEnd() {
        customer.setShowDesiredItem(false);
    }

    @Nullable
    @Override
    public CustomerState tick() {
        super.tick();
        if (customer.getSeatedPos() == null) {
            return new NeedsReseatedState(customer, this);
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        if (event instanceof FoodServedEvent servedEvent) {
            BlockPos seatPos = this.customer.getSeatedPos();
            if (seatPos != null
                    && customer.getLevel().getBlockEntity(servedEvent.pos) instanceof PlateEntity plateEntity
                    && customer.desiresStack(plateEntity.getStack())) {

                // Check if the food was served 1 block up and is N/S/E/W of the seat
                for (Direction direction : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
                    BlockPos possiblePlate = seatPos.above().relative(direction);
                    if (servedEvent.pos.equals(possiblePlate) && !servedEvent.isClaimed()) {
                        servedEvent.claim(customer);
                        customer.setEatingAt(servedEvent.pos);
                        addBonuses();
                        return new EatingState(customer, plateEntity.getStack().copy(), servedEvent.pos);
                    }
                }
            }
        }
        return null;
    }

    public void addBonuses() {
        customer.addHappiness(getSecondsRemaining() / 2);
    }
}
