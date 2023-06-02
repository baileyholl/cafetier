package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.block.PlateEntity;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.mixin.LivingEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EatingState extends CustomerState {

    public int ticksEating;
    public int ticksToEat;
    public ItemStack eatingStack;
    public BlockPos eatingAt;

    public EatingState(Customer customer, ItemStack eatingStack, BlockPos eatingAt) {
        super(customer);
        this.eatingStack = eatingStack;
        this.eatingAt = eatingAt;
        ticksToEat = customer.timeToEat();

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
        ticksEating++;
        if(customer.getLevel().isLoaded(eatingAt) && customer.getLevel().getBlockEntity(eatingAt) instanceof PlateEntity plateEntity){
            ItemStack stack = plateEntity.getStack();
            customer.spawnItemParticles(stack, 10);
            customer.playSound(customer.getEatingSound(stack), 0.3f + 0.5F * (float)customer.getRandom().nextInt(2), (customer.getRandom().nextFloat() - customer.getRandom().nextFloat()) * 0.2F + 1.0F);

        }
        if (ticksEating >= ticksToEat) {
            if(customer.getLevel().isLoaded(eatingAt) && customer.getLevel().getBlockEntity(eatingAt) instanceof PlateEntity plateEntity){
                LivingEntityAccessor livingEntityAccessor = (LivingEntityAccessor) customer;
                livingEntityAccessor.callAddEatEffect(eatingStack, customer.getLevel(), customer);
                plateEntity.setStack(ItemStack.EMPTY);
            }
            return new WaitingToPayState(customer);
        }
        return null;
    }

    @Nullable
    @Override
    public CustomerState onEvent(IStateEvent event) {
        return null;
    }
}
