package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.statemachine.cafe.GameSetupState;
import com.hollingsworth.cafetier.api.statemachine.SimpleStateMachine;
import com.hollingsworth.cafetier.api.statemachine.cafe.GameTeardownState;
import com.hollingsworth.cafetier.block.ManagementDeskEntity;

/**
 * An instance of a cafe game.
 *
 */
public class CafeGame {
    public Cafe cafe;
    public ManagementDeskEntity desk;
    public int maxWave;

    public SimpleStateMachine gameSm;
    public CustomerManager customerManager = new CustomerManager();
    public ScoreManager scoreManager = new ScoreManager();

    public CafeGame(Cafe cafe, ManagementDeskEntity desk) {
        this.cafe = cafe;
        this.desk = desk;
        gameSm = new SimpleStateMachine(new GameSetupState(this));
        maxWave = 2;
    }

    public void tick() {
        gameSm.tick();
        customerManager.tick();
    }

    public void onGameEvent(IStateEvent event){
        gameSm.onEvent(event);
        customerManager.onEvent(event);
        scoreManager.onEvent(event);
    }

    public boolean isDone(){
        return gameSm.getCurrentState() instanceof GameTeardownState;
    }
}
