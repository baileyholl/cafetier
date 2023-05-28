package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import org.jetbrains.annotations.Nullable;

public class SpawnCustomerWavesState implements IState {

    public CafeGame cafeGame;
    public int numSpawned;
    public int ticksToNextSpawn;
    public int maxSpawns;

    public SpawnCustomerWavesState(CafeGame cafeGame) {
        this.cafeGame = cafeGame;
        maxSpawns = 3;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Nullable
    @Override
    public IState tick() {
        if(numSpawned >= maxSpawns){
            cafeGame.scoreManager.wavesCompleted++;
            if(cafeGame.scoreManager.wavesCompleted >= cafeGame.maxWave){
                return new GameTeardownState();
            }else{
                return new TakeBreakState(cafeGame);
            }
        }
        if(ticksToNextSpawn <= 0){
            spawnCustomer();
        }else{
            ticksToNextSpawn--;
        }
        return null;
    }

    public void spawnCustomer(){
        ticksToNextSpawn = getNextTickSpawn();
        numSpawned++;
    }

    public int getNextTickSpawn(){
        return (int) (Math.random() * 100) + 50;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
