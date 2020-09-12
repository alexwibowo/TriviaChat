package com.wibowo.machinia;

import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class StateMachine<C extends Context> {

    @Nullable
    private Function<String, Command> commandParser;

    public void process(final C context,
                        final String commandString) {
        process(context, commandParser.apply(commandString));
    }

    public State<C> process(final C context,
                        final Command command) {
        final State<C> currentState = context.currentState();
        final State<C> newState = currentState.onCommand(context, command);
        if (!newState.equals(currentState)) {
            currentState.onExit(context);
        }
        newState.onEnter(context);
        return newState;
    }


}
