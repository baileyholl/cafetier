package com.hollingsworth.cafetier.api.wave;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.entity.VillagerCustomer;
import com.hollingsworth.cafetier.common.util.RandUtil;
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
        double minCustomers = Math.max(1, (double)game.numSeats / 3.0  + wave);
        double maxCustomers = Math.max(2, (double)game.numSeats / 3.0 + wave) + 1;
        double randomCustomers = RandUtil.inRange(minCustomers, maxCustomers);
        int numCustomers = (int) Math.round(randomCustomers);
        int maxWaveTime = 20 * 180 * 1;
        int numberSplits = 3;
        int ticksPerSplit = maxWaveTime / numberSplits;
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
