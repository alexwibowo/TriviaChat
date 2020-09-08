package com.wibowo.games.triviachat.statemachine.commands;

import com.wibowo.machinia.Command;

public final class StartQuizCommand implements Command {
    public static final StartQuizCommand INSTANCE = new StartQuizCommand();

    @Override
    public String toString() {
        return "START";
    }
}
