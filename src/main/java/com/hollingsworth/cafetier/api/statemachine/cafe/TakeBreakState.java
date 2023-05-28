package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import org.jetbrains.annotations.Nullable;

public class TakeBreakState implements IState {

    public CafeGame cafeGame;
    public int ticksLeft;

    public TakeBreakState(CafeGame cafeGame) {
        this.cafeGame = cafeGame;
        ticksLeft = 500;
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
            return new SpawnCustomerWavesState(cafeGame);
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
