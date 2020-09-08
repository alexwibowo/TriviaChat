package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.machinia.Command;
import com.wibowo.games.triviachat.statemachine.commands.Reset;
import com.wibowo.games.triviachat.statemachine.commands.StartQuizCommand;
import com.wibowo.machinia.State;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class QuizReady implements State<ChatStateMachineContext> {
    public static final QuizReady INSTANCE = new QuizReady();

    @Override
    public List<Command> availableCommands(final ChatStateMachineContext context) {
        return Arrays.asList(
                StartQuizCommand.INSTANCE,
                Reset.INSTANCE
        );
    }

    @Override
    public Command parseCommand(final String commandString) {
        return StartQuizCommand.INSTANCE;
    }

    @Override
    public List<String> machineResponses(final ChatStateMachineContext context) {
        return Collections.singletonList(
                "I'm ready whenever you are"
        );
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Command command) {
        return QuizInProgress.INSTANCE;
    }

}
