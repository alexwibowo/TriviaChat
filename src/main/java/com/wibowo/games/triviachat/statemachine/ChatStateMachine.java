package com.wibowo.games.triviachat.statemachine;

import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.InvalidAnswer;
import com.wibowo.games.triviachat.statemachine.answers.Reset;
import com.wibowo.games.triviachat.statemachine.states.InitialState;
import com.wibowo.games.triviachat.statemachine.states.State;
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

    public List<Answer> availableUserOptions() {
        return currentState.availableUserOptions(chatStateMachineContext);
    }

    public State process(final String answerString) {
        final Answer answer = parseAnswer(answerString);
        if (answer == Reset.INSTANCE) {
            currentState = InitialState.INSTANCE;
            currentState.onEnter(chatStateMachineContext);
        } else if (answer == InvalidAnswer.INSTANCE) {
            // nothing
        } else {
            currentState = currentState.onCommand(chatStateMachineContext, answer);
            currentState.onEnter(chatStateMachineContext);
        }
        return currentState;
    }

    private Answer parseAnswer(final String answerString) {
        if (answerString.equals("RESET")) {
            return Reset.INSTANCE;
        }
        try {
            return currentState.parseAnswer(answerString);
        } catch (final Exception e) {
            LOGGER.error("Invalid answer", e);
            return InvalidAnswer.INSTANCE;
        }
    }
}
