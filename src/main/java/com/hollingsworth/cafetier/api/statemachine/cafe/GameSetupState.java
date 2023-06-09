package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.wave.WaveBuilder;
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
    public GameState tick() {
        WaveBuilder waveBuilder = new WaveBuilder(game);
        waveSchedules.add(waveBuilder.balancedSchedule(1));
        waveSchedules.add(waveBuilder.balancedSchedule(2));
        waveSchedules.add(waveBuilder.balancedSchedule(3));
        return new SpawnCustomerWavesState(game, waveSchedules);
    }

    @Nullable
    @Override
    public GameState onEvent(IStateEvent event) {
        return null;
    }
}
