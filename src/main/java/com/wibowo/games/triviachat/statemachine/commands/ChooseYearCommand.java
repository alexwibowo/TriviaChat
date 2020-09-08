package com.wibowo.games.triviachat.statemachine.commands;

import com.wibowo.machinia.Command;

import java.util.Objects;

public final class ChooseYearCommand implements Command {
    private final int year;

    public ChooseYearCommand(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChooseYearCommand that = (ChooseYearCommand) o;
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
