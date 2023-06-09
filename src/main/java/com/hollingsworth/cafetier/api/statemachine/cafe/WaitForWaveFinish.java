package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import org.jetbrains.annotations.Nullable;

public class WaitForWaveFinish extends GameState{

    public WaitForWaveFinish(CafeGame game) {
        super(game);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Nullable
    @Override
    public IState<?> tick() {
        return null;
    }

    @Nullable
    @Override
    public IState<?> onEvent(IStateEvent event) {
        return null;
    }
}
