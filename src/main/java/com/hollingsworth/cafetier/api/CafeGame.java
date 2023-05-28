package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.GameSetupState;
import com.hollingsworth.cafetier.api.statemachine.SimpleStateMachine;
import com.hollingsworth.cafetier.block.ManagementDeskEntity;

/**
 * An instance of a cafe game.
 *
 */
public class CafeGame {
    public Cafe cafe;
    public ManagementDeskEntity desk;

    private SimpleStateMachine gameSm;

    public CafeGame(Cafe cafe, ManagementDeskEntity desk) {
        this.cafe = cafe;
        this.desk = desk;
        gameSm = new SimpleStateMachine(new GameSetupState(this));
    }

    public void tick() {
        gameSm.tick();
    }
}
