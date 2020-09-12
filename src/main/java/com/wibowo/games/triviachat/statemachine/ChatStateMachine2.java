package com.wibowo.games.triviachat.statemachine;

import com.wibowo.games.triviachat.statemachine.commands.InvalidCommand;
import com.wibowo.games.triviachat.statemachine.commands.Reset;
import com.wibowo.games.triviachat.statemachine.states.InitialState;
import com.wibowo.machinia.Command;
import com.wibowo.machinia.State;
import com.wibowo.machinia.StateMachine;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ChatStateMachine2 extends StateMachine<ChatStateMachineContext> {

    private final Function<String, Command> commandParser;

    public ChatStateMachine2(final @NotNull Function<String, Command> commandParser) {
        this.commandParser = commandParser;
    }

    @Override
    public State<ChatStateMachineContext> process(final ChatStateMachineContext context,
                                                  final Command command) {
        State<ChatStateMachineContext> currentState = context.currentState();
        if (command == Reset.INSTANCE) {
            currentState.onExit(context);
            currentState = InitialState.INSTANCE;
            currentState.onEnter(context);
            return currentState;
        } else if (command == InvalidCommand.INSTANCE) {
            return currentState;
        } else {
            return super.process(context, command);
        }
    }
}
