package com.hollingsworth.cafetier.api.statemachine;

import javax.annotation.Nullable;

public interface IState {

    /**
     * When the state is first entered.
     */
    void onStart();

    /**
     * When the state is exited.
     */
    void onEnd();

    /**
     * Returns a new state if the state is finished, otherwise returns null.
     */
    @Nullable IState tick();

    /**
     * Returns a new state if applicable, otherwise returns null.
     */
    @Nullable IState onEvent(IStateEvent event);

}
