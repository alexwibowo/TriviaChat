package com.wibowo.games.triviachat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class QuestionSet implements Iterable<Question>{
    private final int setNumber;
    @NotNull
    private final List<Question> questions;

    public QuestionSet(final int setNumber) {
        this.setNumber = setNumber;
        this.questions = new ArrayList<>();
    }

    public int getSetNumber() {
        return setNumber;
    }

    public QuestionSet addQuestion(final @NotNull Question question) {
        this.questions.add(question);
        return this;
    }

    @NotNull
    @Override
    public Iterator<Question> iterator() {
        return questions.iterator();
    }
}
