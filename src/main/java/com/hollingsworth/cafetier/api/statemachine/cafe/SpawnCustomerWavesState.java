package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.wave.WaveBuilder;
import com.hollingsworth.cafetier.api.wave.WaveSchedule;
import org.jetbrains.annotations.Nullable;

public class SpawnCustomerWavesState extends GameState {
    public WaveSchedule waveSchedule;

    public SpawnCustomerWavesState(CafeGame cafeGame) {
        super(cafeGame);
        waveSchedule = new WaveBuilder(cafeGame, 1).balancedSchedule();
    }

    @Override
    public void onStart() {}

    @Override
    public void onEnd() {}

    @Nullable
    @Override
    public IState tick() {
        if(waveSchedule.isDone()){
            return new GameTeardownState();
        }else{
            waveSchedule.tick(game);
        }
        return null;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
