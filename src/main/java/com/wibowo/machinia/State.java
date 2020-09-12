package com.wibowo.machinia;

import java.util.List;

public interface State<C extends Context> {

    default void onEnter(final C context) {
        // default: do nothing
    }

    State<C> onCommand(C context,
                       Command command);

    default void onExit(final C context) {
        // default: do nothing
    }

    List<String> machineResponses(final C context);

    List<Command> availableCommands(final C context);

    Command parseCommand(String commandString);
}
