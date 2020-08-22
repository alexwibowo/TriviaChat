package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.Question;
import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.ChooseQuestionAnswer;
import com.wibowo.games.triviachat.statemachine.answers.Reset;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class QuizInProgress implements State{
    public static final QuizInProgress INSTANCE = new QuizInProgress();

    @Override
    public List<Answer> availableUserOptions(final ChatStateMachineContext context) {
        final Question currentQuestion = context.getCurrentQuestion();
        final List<Answer> answers = currentQuestion.getOptions().stream()
                .map(ChooseQuestionAnswer::new)
                .collect(Collectors.toList());
        answers.add(Reset.INSTANCE);
        return answers;
    }

    @Override
    public Answer parseAnswer(final String answerString) {
        return new ChooseQuestionAnswer(answerString);
    }

    @Override
    public void onEnter(final ChatStateMachineContext context) {
        context.pickNextQuestion();
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Answer answer) {
        final ChooseQuestionAnswer chosenAnswer = (ChooseQuestionAnswer) answer;
        if (context.getCurrentQuestion().getCorrectAnswerIndex() == chosenAnswer.getIndex()) {
            return INSTANCE;
        } else {
            return WrongAnswerChosen.INSTANCE;
        }
    }

    @Override
    public List<String> machineResponses(final ChatStateMachineContext context) {
        final Question currentQuestion = context.getCurrentQuestion();
        return Arrays.asList(
                currentQuestion.getQuestionText()
        );
    }

}
