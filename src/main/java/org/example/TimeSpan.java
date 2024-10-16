package org.example;

import org.example.model.IllegalInputTimeSpanException;
import org.example.model.IllegalScaleFactorException;
import org.example.model.NegativeTimeSpanException;

public class TimeSpan {
    private int hours;
    private int minutes;

    public TimeSpan() {
        this(0, 0);
    }

    public TimeSpan(int minutes) {
        this(0, minutes);
    }

    public TimeSpan(int hours, int minutes) {
        validateTime(hours, minutes);
        this.hours = hours;
        this.minutes = minutes;
    }

    public TimeSpan(TimeSpan timeSpan) {
        this(timeSpan.getHours(), timeSpan.getMinutes());
    }

    public void add(int hours, int minutes) {
        validateTime(hours, minutes);
        addMinutes(hours * 60 + minutes);
    }

    public void add(int minutes) {
        if (minutes < 0) {
            throw new IllegalInputTimeSpanException("Invalid minutes");
        }
        addMinutes(minutes);
    }

    public void add(TimeSpan timeSpan) {
        add(timeSpan.getHours(), timeSpan.getMinutes());
    }

    public void subtract(int hours, int minutes) {
        validateTime(hours, minutes);
        int totalSubtractMinutes = hours * 60 + minutes;
        subtractMinutes(totalSubtractMinutes);
    }

    public void subtract(int minutes) {
        if (minutes < 0) {
            throw new IllegalInputTimeSpanException("Invalid minutes");
        }
        subtractMinutes(minutes);
    }

    public void subtract(TimeSpan timeSpan) {
        subtract(timeSpan.getHours(), timeSpan.getMinutes());
    }

    public double getTotalHours() {
        return this.hours + (double) this.minutes / 60;
    }

    public int getTotalMinutes() {
        return this.hours * 60 + this.minutes;
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void scale(int factor) {
        if (factor <= 0) {
            throw new IllegalScaleFactorException("Factor must be greater than zero");
        }
        addMinutes((this.hours * 60 + this.minutes) * (factor - 1)); // Множимо увесь час і додаємо результат.
    }

    // Приватні методи

    private void addMinutes(int minutesToAdd) {
        this.hours += minutesToAdd / 60;
        this.minutes += minutesToAdd % 60;
        if (this.minutes >= 60) {
            this.hours += this.minutes / 60;
            this.minutes = this.minutes % 60;
        }
    }

    private void subtractMinutes(int minutesToSubtract) {
        int totalCurrentMinutes = this.getTotalMinutes();
        if (minutesToSubtract > totalCurrentMinutes) {
            throw new NegativeTimeSpanException("Cannot subtract a larger TimeSpan");
        }

        totalCurrentMinutes -= minutesToSubtract;
        this.hours = totalCurrentMinutes / 60;
        this.minutes = totalCurrentMinutes % 60;
    }

    private void validateTime(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) {
            throw new IllegalInputTimeSpanException("Invalid hours or minutes");
        }
    }
}

