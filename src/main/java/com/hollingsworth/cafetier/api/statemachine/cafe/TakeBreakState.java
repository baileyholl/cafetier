package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.wave.WaveSchedule;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TakeBreakState extends GameState {

    public int ticksLeft;
    public List<WaveSchedule> remainingSchedules;

    public TakeBreakState(CafeGame cafeGame, List<WaveSchedule> remainingSchedules) {
        super(cafeGame);
        ticksLeft = 20 * 30;
        this.remainingSchedules = remainingSchedules;
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
        if(ticksLeft <= 0){
            return new SpawnCustomerWavesState(game, remainingSchedules);
        }else{
            ticksLeft--;
        }
        return null;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
