package com.hollingsworth.cafetier.api.wave;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.entity.VillagerCustomer;
import com.hollingsworth.cafetier.common.util.RandUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class WaveBuilder {

    public WaveSchedule waveSchedule;
    public List<BlockPos> spawnPoints;
    public CafeGame game;
    public int difficulty;

    public WaveBuilder(CafeGame game, int difficulty){
        this.game = game;
        spawnPoints = List.of(game.getValidSpawnPos((ServerLevel) game.desk.getLevel()));
        this.difficulty = difficulty;
    }


    public WaveSchedule balancedSchedule(){
        List<SpawnGroup> groups = new ArrayList<>();
        int numCustomers = RandUtil.inclusiveRange(3 + difficulty, 5 + difficulty);
        int maxWaveTime = 20 * 60 * 5;
        int numberSplits = 3;
        int ticksPerSplit = maxWaveTime / numberSplits;
        for(int i = 0; i < numberSplits; i++){
            List<Customer> customers = new ArrayList<>();
            for(int j = 0; j < numCustomers; j++){
                customers.add(getCustomer(game.getLevel(), game.cafe));
            }
            groups.add(new SpawnGroup(customers, i * ticksPerSplit));
        }
        return new WaveSchedule(groups, spawnPoints);
    }

    public Customer getCustomer(Level level, Cafe cafe){
        return new VillagerCustomer(level, cafe);
    }
}
