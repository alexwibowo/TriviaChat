package com.wibowo.games.triviachat.statemachine.answers;

public final class StartTrivia implements Answer {
    public static final StartTrivia INSTANCE = new StartTrivia();

    @Override
    public String toString() {
        return "START";
    }
}
