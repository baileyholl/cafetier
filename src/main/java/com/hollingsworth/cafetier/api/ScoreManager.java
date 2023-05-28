package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;

public class ScoreManager {
    public int wavesCompleted;
    public int customersServed;
    public int customersLost;
    public int penalty;

    public ScoreManager() {
        wavesCompleted = 0;
        customersServed = 0;
        customersLost = 0;
        penalty = 0;
    }

    public int getScore() {
        return wavesCompleted * 100 + customersServed * 10 - customersLost * 30 - penalty;
    }

    public void onEvent(IStateEvent event){

    }
}
