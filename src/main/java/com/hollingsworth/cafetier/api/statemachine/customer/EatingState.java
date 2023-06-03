package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.block.PlateEntity;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.mixin.LivingEntityAccessor;
import com.hollingsworth.cafetier.common.util.RandUtil;
import com.mojang.math.Vector3d;
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
        customer.forcedLookAt = null;
    }

    @Nullable
    @Override
    public CustomerState tick() {
        if(customer.getSeatedPos() == null){
            return new NeedsReseatedState(customer, this);
        }
        ticksEating++;
        boolean plateLoaded = customer.getLevel().isLoaded(eatingAt) && customer.getLevel().getBlockEntity(eatingAt) instanceof PlateEntity;
        if(!plateLoaded || !customer.desiresStack(eatingStack)){
            customer.loseHappiness(10);
            return new WaitingForFoodState(customer, customer.getDesiredItem());
        }
        PlateEntity plateEntity = (PlateEntity) customer.getLevel().getBlockEntity(eatingAt);
        ItemStack stack = plateEntity.getStack();
        customer.forcedLookAt = new Vector3d(eatingAt.getX() + 0.5, eatingAt.getY() + 0.5, eatingAt.getZ() + 0.5);
        if(getRandom().nextInt(4) == 0) {
            plateEntity.spawnEatingParticles(RandUtil.inclusiveRange(1, 5));
            customer.playSound(customer.getEatingSound(stack), 0.3f + 0.3F * (float) customer.getRandom().nextInt(2), (customer.getRandom().nextFloat() - customer.getRandom().nextFloat()) * 0.2F + 1.0F);
        }

        if (ticksEating >= ticksToEat) {
            LivingEntityAccessor livingEntityAccessor = (LivingEntityAccessor) customer;
            livingEntityAccessor.callAddEatEffect(eatingStack, customer.getLevel(), customer);
            plateEntity.setStack(ItemStack.EMPTY);
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
