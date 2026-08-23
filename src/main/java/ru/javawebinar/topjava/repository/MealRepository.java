package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int userId);

    boolean delete(int userId, int mealId);

    Meal get(int userId, int mealId);

    List<Meal> getBetween(int userId, LocalDateTime start, LocalDateTime end);

    List<Meal> getAll(int userId);
}
