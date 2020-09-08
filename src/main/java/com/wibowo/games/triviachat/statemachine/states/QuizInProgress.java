package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.Question;
import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.machinia.Command;
import com.wibowo.games.triviachat.statemachine.commands.ChooseQuestionCommand;
import com.wibowo.machinia.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class QuizInProgress implements State<ChatStateMachineContext> {
    public static final QuizInProgress INSTANCE = new QuizInProgress();
    private static final List<String> optionLetters = Arrays.asList("A","B","C","D","E","F","G","H");

    @Override
    public List<Command> availableCommands(final ChatStateMachineContext context) {
        final Question currentQuestion = context.getCurrentQuestion();

        final List<String> options = currentQuestion.getOptions();
        final List<Command> commands = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            commands.add(new ChooseQuestionCommand(optionLetters.get(i)));
        }
        return commands;
    }

    @Override
    public Command parseCommand(final String commandString) {
        return new ChooseQuestionCommand(commandString);
    }

    @Override
    public void onEnter(final ChatStateMachineContext context) {
        context.pickNextQuestion();
    }

    @Override
    public State onCommand(final ChatStateMachineContext context,
                           final Command command) {
        final ChooseQuestionCommand chosenAnswer = (ChooseQuestionCommand) command;
        if (context.getCurrentQuestion().getCorrectAnswerIndex() == chosenAnswer.getIndex()) {
            return INSTANCE;
        } else {
            return WrongAnswerChosen.INSTANCE;
        }
    }

    @Override
    public List<String> machineResponses(final ChatStateMachineContext context) {
        final Question currentQuestion = context.getCurrentQuestion();
        final List<String> options = currentQuestion.getOptions();

        final StringBuilder builder = new StringBuilder();
        builder.append(currentQuestion.getQuestionText()).append("\n");

        for (int i = 0; i < options.size(); i++) {
            builder.append(optionLetters.get(i)).append(".").append(options.get(i)).append("\n");
        }

        return Arrays.asList(builder.toString());
    }

}
