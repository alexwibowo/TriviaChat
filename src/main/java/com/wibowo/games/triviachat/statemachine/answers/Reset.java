package com.wibowo.games.triviachat.statemachine.answers;

public final class Reset implements Answer {
    public static final Reset INSTANCE = new Reset();

    @Override
    public String toString() {
        return "RESET";
    }
}
