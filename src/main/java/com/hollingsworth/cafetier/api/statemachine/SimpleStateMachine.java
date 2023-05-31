package com.hollingsworth.cafetier.api.statemachine;

import javax.annotation.Nonnull;

public class SimpleStateMachine {
    public static final boolean DEBUG = true;

    protected IState currentState;

    public SimpleStateMachine(@Nonnull IState initialState) {
        currentState = initialState;
        currentState.onStart();
        if(DEBUG){
            System.out.println("Starting state machine with state " + currentState.getClass().getSimpleName());
            System.out.println("=====");
        }
    }

    protected void changeState(@Nonnull IState nextState) {
        if(DEBUG){
            System.out.println("Changing state from " + currentState.getClass().getSimpleName() + " to " + nextState.getClass().getSimpleName());
        }
        currentState.onEnd();
        currentState = nextState;
        currentState.onStart();
    }

    public void tick() {
        IState nextState = currentState.tick();
        if (nextState != null) {
            changeState(nextState);
        }
    }

    public void onEvent(IStateEvent event) {
        IState nextState = currentState.onEvent(event);
        if (nextState != null) {
            changeState(nextState);
        }
    }

    public IState getCurrentState(){
        return currentState;
    }
}
