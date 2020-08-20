package com.wibowo.games.triviachat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Topic implements Iterable<QuestionSet>{
    private final String topicName;

    private final List<QuestionSet> questionSets;

    public Topic(final String topicName) {
        this.topicName = topicName;
        this.questionSets = new ArrayList<>();
    }

    public String getTopicName() {
        return topicName;
    }

    public Topic addQuestionSet(final QuestionSet questionSet) {
        questionSets.add(questionSet);
        return this;
    }

    @NotNull
    @Override
    public Iterator<QuestionSet> iterator() {
        return questionSets.iterator();
    }
}
