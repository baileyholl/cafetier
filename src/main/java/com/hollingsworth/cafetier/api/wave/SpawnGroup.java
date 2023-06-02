package com.hollingsworth.cafetier.api.wave;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.core.BlockPos;

import java.util.List;

public class SpawnGroup {
    public List<Customer> customers;
    public int ticksToSpawn;

    public SpawnGroup(List<Customer> customers, int ticksToSpawn) {
        this.customers = customers;
        this.ticksToSpawn = ticksToSpawn;
    }

    public void spawn(CafeGame game, List<BlockPos> pos){
        for(Customer customer : customers){
            BlockPos randomPos = pos.get(customer.getRandom().nextInt(pos.size()));
            game.spawnCustomer(customer, randomPos);
        }
    }
}
