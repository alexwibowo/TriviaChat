package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.ChooseTopicAnswer;
import com.wibowo.games.triviachat.statemachine.answers.Reset;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class TopicNotDetermined implements State{
    public static final TopicNotDetermined INSTANCE = new TopicNotDetermined();

    @Override
    public List<Answer> availableUserOptions(final ChatStateMachineContext context) {
        List<Answer> collect = context.getAvailableTopics().stream()
                .map(ChooseTopicAnswer::new)
                .collect(Collectors.toList());
        collect.add(Reset.INSTANCE);
        return collect;
    }

    @Override
    public Answer parseAnswer(final String answerString) {
        return new ChooseTopicAnswer(answerString);
    }

    @Override
    public List<String> machineResponses(final ChatStateMachineContext context) {
        return Arrays.asList(
                "Awesome",
                "Choose what topic you would like to have"
        );
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Answer answer) {
        if (answer instanceof ChooseTopicAnswer) {
            final ChooseTopicAnswer chooseTopicAnswer = (ChooseTopicAnswer) answer;
            context.setCurrentTopic(chooseTopicAnswer.getTopic());
            return QuizReady.INSTANCE;
        }
        throw new RuntimeException("unexpected answer!");
    }

}
