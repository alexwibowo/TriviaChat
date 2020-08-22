package com.wibowo.games.triviachat.statemachine.answers;

public final class StartQuizAnswer implements Answer {
    public static final StartQuizAnswer INSTANCE = new StartQuizAnswer();

    @Override
    public String toString() {
        return "START";
    }
}
