package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import org.jetbrains.annotations.Nullable;

public class GameSetupState implements IState {

    public CafeGame game;

    public GameSetupState(CafeGame cafeGame) {
        this.game = cafeGame;
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
