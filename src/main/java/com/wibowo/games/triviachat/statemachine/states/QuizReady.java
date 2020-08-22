package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.Reset;
import com.wibowo.games.triviachat.statemachine.answers.StartQuizAnswer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class QuizReady implements State{
    public static final QuizReady INSTANCE = new QuizReady();

    @Override
    public List<Answer> availableUserOptions(final ChatStateMachineContext context) {
        return Arrays.asList(
                StartQuizAnswer.INSTANCE,
                Reset.INSTANCE
        );
    }

    @Override
    public Answer parseAnswer(final String answerString) {
        return StartQuizAnswer.INSTANCE;
    }

    @Override
    public List<String> machineResponses(final ChatStateMachineContext context) {
        return Collections.singletonList(
                "I'm ready whenever you are"
        );
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Answer answer) {
        return QuizInProgress.INSTANCE;
    }

}
