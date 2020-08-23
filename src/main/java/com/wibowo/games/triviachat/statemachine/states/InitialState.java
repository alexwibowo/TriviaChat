package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.StartTrivia;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class InitialState implements State {
    public static final InitialState INSTANCE = new InitialState();

    @Override
    public List<Answer> availableUserOptions(final ChatStateMachineContext context) {
        return Collections.singletonList(StartTrivia.INSTANCE);
    }

    @Override
    public Answer parseAnswer(final String answerString) {
        if (answerString.equals("START")) {
            return StartTrivia.INSTANCE;
        }
        return null;
    }

    @Override
    public List<String> machineResponses(final ChatStateMachineContext context) {
        return Arrays.asList(
                String.format("Hi %s, welcome to Monash Medical Exam Prep", context.getUserProfile().firstName()),
                "My name is Bob, I'm a robot",
                "I can help you prepare for your exam by asking some questions",
                "Please use the button below to continue to chat with me"
        );
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Answer answer) {
        if (answer == StartTrivia.INSTANCE) {
            return YearNotDetermined.INSTANCE;
        }
        return this;
    }

}
