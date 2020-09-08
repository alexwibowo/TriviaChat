package com.wibowo.games.triviachat.statemachine;

import com.wibowo.machinia.Command;
import com.wibowo.games.triviachat.statemachine.commands.InvalidCommand;
import com.wibowo.games.triviachat.statemachine.commands.Reset;
import com.wibowo.games.triviachat.statemachine.states.InitialState;
import com.wibowo.machinia.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class ChatStateMachine {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatStateMachine.class.getName());

    private ChatStateMachineContext chatStateMachineContext;
    private State currentState;

    public ChatStateMachine(final ChatStateMachineContext chatStateMachineContext) {
        this.currentState = InitialState.INSTANCE;
        this.chatStateMachineContext = chatStateMachineContext;
    }

    public List<String> machineResponses() {
        return currentState.machineResponses(chatStateMachineContext);
    }

    public List<Command> availableUserOptions() {
        return currentState.availableCommands(chatStateMachineContext);
    }

    public State process(final String answerString) {
        final Command command = parseAnswer(answerString);
        if (command == Reset.INSTANCE) {
            currentState = InitialState.INSTANCE;
            currentState.onEnter(chatStateMachineContext);
        } else if (command == InvalidCommand.INSTANCE) {
            // nothing
        } else {
            final State newState = this.currentState.onCommand(chatStateMachineContext, command);
            if (newState != currentState) {
                this.currentState.onExit(chatStateMachineContext);
            }
            this.currentState = newState;
            this.currentState.onEnter(chatStateMachineContext);
        }
        return currentState;
    }

    private Command parseAnswer(final String answerString) {
        if (answerString.equals("RESET")) {
            return Reset.INSTANCE;
        }
        try {
            return currentState.parseCommand(answerString);
        } catch (final Exception e) {
            LOGGER.error("Invalid answer", e);
            return InvalidCommand.INSTANCE;
        }
    }
}
