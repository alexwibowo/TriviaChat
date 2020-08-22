package com.wibowo.games.triviachat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class Topic implements Iterable<QuestionSet>{
    public static Random random = new Random(System.currentTimeMillis());
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

    public QuestionSet randomQuestionSet() {
        return questionSets.get(random.nextInt(questionSets.size()));
    }

    @NotNull
    @Override
    public Iterator<QuestionSet> iterator() {
        return questionSets.iterator();
    }
}
