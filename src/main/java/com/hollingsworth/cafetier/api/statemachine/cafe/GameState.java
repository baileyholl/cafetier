package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.statemachine.IState;
import net.minecraft.server.level.ServerLevel;

public abstract class GameState implements IState {
    public CafeGame game;

    public GameState(CafeGame game) {
        this.game = game;
    }

    public ServerLevel getLevel(){
        return game.getLevel();
    }
}
