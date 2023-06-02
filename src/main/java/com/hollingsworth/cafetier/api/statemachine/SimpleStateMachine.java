package com.hollingsworth.cafetier.api.statemachine;

import javax.annotation.Nonnull;

public class SimpleStateMachine<State extends IState, Event extends IStateEvent> {
    public static final boolean DEBUG = true;

    protected State currentState;

    public SimpleStateMachine(@Nonnull State initialState) {
        currentState = initialState;
        currentState.onStart();
        if(DEBUG){
            System.out.println("Starting state machine with state " + currentState.getClass().getSimpleName());
            System.out.println("=====");
        }
    }

    protected void changeState(@Nonnull State nextState) {
        if(DEBUG){
            System.out.println("Changing state from " + currentState.getClass().getSimpleName() + " to " + nextState.getClass().getSimpleName());
        }
        currentState.onEnd();
        currentState = nextState;
        currentState.onStart();
    }

    public void tick() {
        if(currentState == null)
            return;
        IState nextState = currentState.tick();
        if (nextState != null) {
            changeState((State)nextState);
        }
    }

    public void onEvent(Event event) {
        IState nextState = currentState.onEvent(event);
        if (nextState != null) {
            changeState((State) nextState);
        }
    }

    public State getCurrentState(){
        return currentState;
    }
}
