package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.api.game_events.InteractEvent;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WaitingToOrderState extends CustomerState {

    public int ticksWaited = 0;
    public int maxWait;
    public ItemStack desiredStack;

    public WaitingToOrderState(Customer customer){
        super(customer);
        maxWait = customer.maxWaitToOrder();
        desiredStack = getGame().menuStacks.get(customer.getRandom().nextInt(customer.game.menuStacks.size()));
    }

    @Override
    public void onStart() {
        customer.setDisplayIcon(Cafetier.MODID + ":textures/gui/icons/ready_to_order.png");
    }

    @Override
    public void onEnd() {
        customer.setDisplayIcon("");
    }

    @Nullable
    @Override
    public CustomerState tick() {
        if(customer.getSeatedPos() == null){
            return new NeedsReseatedState(customer, this);
        }
        ticksWaited++;
        if(ticksWaited >= maxWait && ticksWaited % 20 == 0){
            customer.loseHappiness(1);
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        if(event instanceof InteractEvent){
            customer.acknowledgeServer(((InteractEvent) event).player);
            return new WaitingForFoodState(customer, desiredStack);
        }
        return null;
    }
}
