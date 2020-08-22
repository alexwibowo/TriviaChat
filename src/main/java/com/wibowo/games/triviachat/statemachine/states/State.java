package com.wibowo.games.triviachat.statemachine.states;

import com.wibowo.games.triviachat.statemachine.ChatStateMachineContext;
import com.wibowo.games.triviachat.statemachine.answers.Answer;

import java.util.List;

public interface State {

    default void onEnter(final ChatStateMachineContext context) {
        // default: do nothing
    }

    State onCommand(final ChatStateMachineContext context,
                    final Answer answer);

    List<String> machineResponses(final ChatStateMachineContext context);

    List<Answer> availableUserOptions(final ChatStateMachineContext context);

    Answer parseAnswer(String answerString);
}
