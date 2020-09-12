package com.wibowo.machinia;

import java.util.List;

public interface Context {

    long id();

    <T extends Context> State<T> currentState();

    default List<Command> availableCommands() {
        return currentState().availableCommands(this);
    }

    default List<String> machineResponses() {
        return currentState().machineResponses(this);
    }
}
