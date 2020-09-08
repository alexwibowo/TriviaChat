package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.machinia.Command;
import com.wibowo.games.triviachat.statemachine.commands.ChooseTopicCommand;
import com.wibowo.games.triviachat.statemachine.commands.Reset;
import com.wibowo.machinia.State;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class TopicNotDetermined implements State<ChatStateMachineContext> {
    public static final TopicNotDetermined INSTANCE = new TopicNotDetermined();

    @Override
    public List<Command> availableCommands(final ChatStateMachineContext context) {
        List<Command> collect = context.getAvailableTopics().stream()
                .map(ChooseTopicCommand::new)
                .collect(Collectors.toList());
        collect.add(Reset.INSTANCE);
        return collect;
    }

    @Override
    public Command parseCommand(final String commandString) {
        return new ChooseTopicCommand(commandString);
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
                           final Command command) {
        if (command instanceof ChooseTopicCommand) {
            final ChooseTopicCommand chooseTopicAnswer = (ChooseTopicCommand) command;
            context.setCurrentTopic(chooseTopicAnswer.getTopic());
            return QuizReady.INSTANCE;
        }
        throw new RuntimeException("unexpected answer!");
    }

}
