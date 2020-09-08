package com.wibowo.games.triviachat.statemachine.commands;

import com.wibowo.machinia.Command;

public final class Reset implements Command {
    public static final Reset INSTANCE = new Reset();

    @Override
    public String toString() {
        return "RESET";
    }
}
