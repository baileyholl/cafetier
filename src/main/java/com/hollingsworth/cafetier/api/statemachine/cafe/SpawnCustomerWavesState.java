package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.wave.WaveSchedule;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpawnCustomerWavesState extends GameState {

    public List<WaveSchedule> waveSchedules;
    public WaveSchedule waveSchedule;

    public SpawnCustomerWavesState(CafeGame cafeGame, List<WaveSchedule> waveSchedules){
        super(cafeGame);
        this.waveSchedules = waveSchedules;
        waveSchedule = waveSchedules.remove(0);
    }

    @Override
    public void onStart() {}

    @Override
    public void onEnd() {}

    @Nullable
    @Override
    public IState tick() {
        if(waveSchedule.isDone() && game.customerManager.trackedCustomers.isEmpty()){
            if(waveSchedules.isEmpty()) {
                return new GameTeardownState();
            }else{
                return new TakeBreakState(game, waveSchedules);
            }
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
