package com.wibowo.games.triviachat.statemachine.commands;

import com.wibowo.machinia.Command;

import java.util.Objects;

public final class ChooseTopicCommand implements Command {
    private final String topic;

    public ChooseTopicCommand(final String topic) {
        this.topic = topic;
    }
    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChooseTopicCommand that = (ChooseTopicCommand) o;
        return Objects.equals(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic);
    }
}
