package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.Question;
import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.NextQuestion;
import com.wibowo.games.triviachat.statemachine.answers.Reset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class WrongAnswerChosen implements State{
    public static final WrongAnswerChosen INSTANCE = new WrongAnswerChosen();
    public static final Random random = new Random(System.currentTimeMillis());

    public static final String[] FUNNY_ANSWERS = new String[]{
            "I would love to say yes, but my dog told me to say no.",
            "My advisors have come to a unanimous decision, and it’s a—NO!",
            "In this world, there are countless of cool things to do. Unfortunately, your idea does fall into such category.",
            "The voices in my head are asking me to say ‘no’ to this one.",
            "That’s such a funny joke! HAHAHAHA!",
            "I’d rather swallow a pillow.",
            "It’s N to the O!",
            "You know what season it is? It’s the season of NO!",
            "That sounds like effort, so no.",
            "Does it involve me moving from where I am right now? If the answer is yes, then I would have to say no.",
            "You should know my answer by the look of disgust on my face.",
            "I would love to say yes, but I actually wouldn’t love to say yes.",
            "Give me an ‘N.’ Give me an ‘O.’ Give me an ‘N-O!’"
    };

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
        final List<String> responses = new ArrayList<>();
        responses.add(FUNNY_ANSWERS[random.nextInt(FUNNY_ANSWERS.length)]);
        responses.add("The correct answer is:");
        responses.add(currentQuestion.getAnswer());
        if (currentQuestion.getExplanation().isPresent()) {
            responses.add("Explanation:");
            responses.add(currentQuestion.getExplanation().get());
        }else{
            responses.add("Bummer. I dont have any explanation to that. Maybe ask my creator to explain it.");
        }
        return responses;
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Answer answer) {
        return QuizInProgress.INSTANCE;
    }
}
