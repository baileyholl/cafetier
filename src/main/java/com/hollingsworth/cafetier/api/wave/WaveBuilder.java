package com.hollingsworth.cafetier.api.wave;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.entity.VillagerCustomer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class WaveBuilder {

    public List<BlockPos> spawnPoints;
    public CafeGame game;

    public WaveBuilder(CafeGame game){
        this.game = game;
        spawnPoints = game.spawnPositions;
    }


    public WaveSchedule balancedSchedule(int wave){
        List<SpawnGroup> groups = new ArrayList<>();
        double maxCustomers = game.numSeats * (1.5 + .25 * wave);
        int maxWaveTime = 20 * 180 * 1 + wave * 20 * 15;
        int numberSplits = 5;
        int numCustomers = Math.max(1, (int) Math.round(maxCustomers) / numberSplits);
        int ticksPerSplit = maxWaveTime / numberSplits;
        System.out.println("Max Customers: " + maxCustomers);
        System.out.println("Max Wave Time: " + maxWaveTime);
        System.out.println("Number Splits: " + numberSplits);
        System.out.println("Num Customers: " + numCustomers);
        System.out.println("Ticks Per Split: " + ticksPerSplit);

        for(int i = 0; i < numberSplits; i++){
            for(int j = 0; j < numCustomers; j++){
                List<Customer> customers = new ArrayList<>();
                customers.add(getCustomer(game.getLevel(), game));
                groups.add(new SpawnGroup(customers, i * ticksPerSplit + j * 45));
            }
        }
        return new WaveSchedule(groups, spawnPoints);
    }

    public Customer getCustomer(Level level, CafeGame game){
        return new VillagerCustomer(level, game);
    }
}
