package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.ChooseYearAnswer;
import com.wibowo.games.triviachat.statemachine.answers.Reset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class YearNotDetermined implements State{
    public static final YearNotDetermined INSTANCE = new YearNotDetermined();

    @Override
    public List<Answer> availableUserOptions(final ChatStateMachineContext context) {
        final ArrayList<Answer> answers = new ArrayList<>();
        int[] availableYears = context.getAvailableYears();
        for (int availableYear : availableYears) {
            answers.add(new ChooseYearAnswer(availableYear));
        }
        answers.add(Reset.INSTANCE);
        return answers;
    }

    @Override
    public Answer parseAnswer(final String answerString) {
        return new ChooseYearAnswer(Integer.parseInt(answerString));
    }

    @Override
    public List<String> machineResponses(final ChatStateMachineContext context) {
        return Arrays.asList(
                "Fabulous",
                "Could you let me know your years of study"
        );
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Answer answer) {
        if (answer instanceof ChooseYearAnswer) {
            final ChooseYearAnswer chooseYearAnswer = (ChooseYearAnswer) answer;
            context.setCurrentYear(chooseYearAnswer.getYear());
            return TopicNotDetermined.INSTANCE;
        }
        throw new RuntimeException("unexpected answer!");
    }

}
