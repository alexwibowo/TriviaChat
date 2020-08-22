package com.wibowo.games.triviachat.statemachine.answers;

import java.util.Objects;

public final class ChooseTopicAnswer implements Answer {
    private final String topic;

    public ChooseTopicAnswer(final String topic) {
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
        ChooseTopicAnswer that = (ChooseTopicAnswer) o;
        return Objects.equals(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic);
    }
}
