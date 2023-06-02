package com.hollingsworth.cafetier.api.wave;

import com.hollingsworth.cafetier.api.CafeGame;
import net.minecraft.core.BlockPos;

import java.util.List;

public class WaveSchedule {

    public List<SpawnGroup> spawnGroups;
    public List<BlockPos> spawnPoints;
    public int ticks;

    public WaveSchedule(List<SpawnGroup> groups, List<BlockPos> spawnPoints){
        this.spawnGroups = groups;
        this.spawnPoints = spawnPoints;
    }

    public void tick(CafeGame game){
        if(spawnGroups.isEmpty()){
            return;
        }
        ticks++;
        SpawnGroup nextGroup = spawnGroups.get(0);
        if(ticks >= nextGroup.ticksToSpawn){
            nextGroup.spawn(game, spawnPoints);
            spawnGroups.remove(0);
        }
    }

    public boolean isDone(){
        return spawnGroups.isEmpty();
    }
}
