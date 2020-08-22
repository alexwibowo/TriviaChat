package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.Question;
import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.NextQuestion;
import com.wibowo.games.triviachat.statemachine.answers.Reset;

import java.util.Arrays;
import java.util.List;

public final class WrongAnswerChosen implements State{
    public static final WrongAnswerChosen INSTANCE = new WrongAnswerChosen();

    @Override
    public List<Answer> availableUserOptions(ChatStateMachineContext context) {
        return Arrays.asList(
                NextQuestion.INSTANCE,
                Reset.INSTANCE
        );
    }

    @Override
    public Answer parseAnswer(final String answerString) {
        return NextQuestion.INSTANCE;
    }

    @Override
    public List<String> machineResponses(final ChatStateMachineContext context) {
        final Question currentQuestion = context.getCurrentQuestion();
        return Arrays.asList(
                "Display the correct answer",
                "Along with explantaion"
        );
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Answer answer) {
        return QuizInProgress.INSTANCE;
    }
}
