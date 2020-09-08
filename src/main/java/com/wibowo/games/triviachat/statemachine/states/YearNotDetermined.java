package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.machinia.Command;
import com.wibowo.games.triviachat.statemachine.commands.ChooseYearCommand;
import com.wibowo.games.triviachat.statemachine.commands.Reset;
import com.wibowo.machinia.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class YearNotDetermined implements State<ChatStateMachineContext> {
    public static final YearNotDetermined INSTANCE = new YearNotDetermined();

    @Override
    public List<Command> availableCommands(final ChatStateMachineContext context) {
        final ArrayList<Command> commands = new ArrayList<>();
        int[] availableYears = context.getAvailableYears();
        for (int availableYear : availableYears) {
            commands.add(new ChooseYearCommand(availableYear));
        }
        commands.add(Reset.INSTANCE);
        return commands;
    }

    @Override
    public Command parseCommand(final String commandString) {
        return new ChooseYearCommand(Integer.parseInt(commandString));
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
                           final Command command) {
        if (command instanceof ChooseYearCommand) {
            final ChooseYearCommand chooseYearAnswer = (ChooseYearCommand) command;
            context.setCurrentYear(chooseYearAnswer.getYear());
            return TopicNotDetermined.INSTANCE;
        }
        throw new RuntimeException("unexpected answer!");
    }

}
