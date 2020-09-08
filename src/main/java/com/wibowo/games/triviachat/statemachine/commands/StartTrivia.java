package com.wibowo.games.triviachat.statemachine.commands;

import com.wibowo.machinia.Command;

public final class StartTrivia implements Command {
    public static final StartTrivia INSTANCE = new StartTrivia();

    @Override
    public String toString() {
        return "START";
    }
}
