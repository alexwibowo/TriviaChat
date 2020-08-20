package com.wibowo.games.triviachat;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class Question {
    @NotNull
    private final String questionText;
    private int correctAnswerIndex;
    private final List<String> options;

    public Question(final @NotNull String questionText) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(questionText));
        this.questionText = questionText;
        this.options = new ArrayList<>();
    }

    @NotNull
    public String getQuestionText() {
        return questionText;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(final int correctAnswerIndex) {
        Preconditions.checkArgument(!options.isEmpty());
        Preconditions.checkArgument(correctAnswerIndex >= 0 && correctAnswerIndex <= options.size() - 1);
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public List<String> getOptions() {
        return options;
    }

    @NotNull
    public Question addOptions(final @NotNull String... option) {
        for (int i = 0; i < option.length; i++) {
            addOption(option[i]);
        }
        return this;
    }
    @NotNull
    public Question addOption(final @NotNull String option) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(option));
        options.add(option);
        return this;
    }

    public String getAnswer() {
        return options.get(correctAnswerIndex);
    }
}
