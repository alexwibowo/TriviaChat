package com.wibowo.games.triviachat.statemachine.answers;

public final class ChooseQuestionAnswer implements Answer {
    private final String option;

    public ChooseQuestionAnswer(String option) {
        this.option = option;
    }

    public int getIndex() {
        switch (option) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
        }
        throw new RuntimeException("WHAT");
    }

    @Override
    public String toString() {
        return option;
    }
}
