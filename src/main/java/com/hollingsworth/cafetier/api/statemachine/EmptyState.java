package com.hollingsworth.cafetier.api.statemachine;

import org.jetbrains.annotations.Nullable;

public class EmptyState implements IState{
    public static EmptyState INSTANCE = new EmptyState();

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Nullable
    @Override
    public IState tick() {
        return null;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
