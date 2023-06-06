package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.wave.WaveSchedule;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GameSetupState extends GameState {

    public List<WaveSchedule> waveSchedules = new ArrayList<>();
    public GameSetupState(CafeGame cafeGame) {
        super(cafeGame);

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public IState tick() {
        return new SpawnCustomerWavesState(game);
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
