package com.wibowo.games.triviachat.statemachine.commands;

import com.wibowo.machinia.Command;

public final class NextQuestion implements Command {

    public static final NextQuestion INSTANCE = new NextQuestion();

    @Override
    public String toString() {
        return "NEXT";
    }
}
