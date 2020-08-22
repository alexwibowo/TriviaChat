package com.wibowo.games.triviachat.statemachine.answers;

import java.util.Objects;

public final class ChooseYearAnswer implements Answer {
    private final int year;

    public ChooseYearAnswer(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChooseYearAnswer that = (ChooseYearAnswer) o;
        return year == that.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year);
    }

    @Override
    public String toString() {
        return "" + year;
    }
}
