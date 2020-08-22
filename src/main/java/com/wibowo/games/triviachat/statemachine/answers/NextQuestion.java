package com.wibowo.games.triviachat.statemachine.answers;

public final class NextQuestion implements Answer {

    public static final NextQuestion INSTANCE = new NextQuestion();

    @Override
    public String toString() {
        return "NEXT";
    }
}
