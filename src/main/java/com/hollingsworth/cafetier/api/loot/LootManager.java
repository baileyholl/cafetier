package com.hollingsworth.cafetier.api.loot;

import com.hollingsworth.cafetier.api.game_events.CustomerPaidEvent;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for rolling loot tables for customers when they pay or the game is complete.
 */
public class LootManager {

    public List<ItemStack> rollTip(Customer customer, int rolls){
        LootTable lootTable = customer.level.getServer().getLootTables().get(customer.getLootTable());
        LootContext.Builder builder = new LootContext.Builder((ServerLevel) customer.level)
                .withRandom(customer.getRandom())
                .withParameter(LootContextParams.THIS_ENTITY, customer)
                .withParameter(LootContextParams.ORIGIN, customer.position());

        LootContext ctx = builder.create(LootContextParamSets.GIFT);
        List<ItemStack> stacks = new ArrayList<>();
        for(int i = 0; i < rolls; i++){
            stacks.addAll(lootTable.getRandomItems(ctx));
        }
        return stacks;
    }

    public void onEvent(IStateEvent event){
        if(event instanceof CustomerPaidEvent paidEvent){
            List<ItemStack> stacks = rollTip(paidEvent.customer, 1);
            for(ItemStack stack : stacks){
                paidEvent.customer.spawnAtLocation(stack, 1.5f);
            }
        }
    }
}
