package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, Integer userId);

    // false if meal does not belong to userId
    boolean delete(int userId, int mealId);

    // null if meal does not belong to userId
    Meal get(int userId, int mealId);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int userId);
}
