package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final DayCaloriesFlag dayCaloriesFlag;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, DayCaloriesFlag dayCalorieState) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.dayCaloriesFlag = dayCalorieState;
    }

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this(dateTime, description, calories, new DayCaloriesFlag(excess));
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
               "dateTime=" + dateTime +
               ", description='" + description + '\'' +
               ", calories=" + calories +
               ", dayCaloriesFlag=" + dayCaloriesFlag +
               '}';
    }

    public static class DayCaloriesFlag {
        private boolean excess;

        public DayCaloriesFlag() {
        }

        public DayCaloriesFlag(boolean excess) {
            this.excess = excess;
        }

        public void setExcess(boolean excess) {
            this.excess = excess;
        }

        @Override
        public String toString() {
            return "excess=" + excess;
        }
    }
}