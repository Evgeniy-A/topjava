package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map <Integer, Map<Integer, Meal>> mealsByUser = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            Map<Integer, Meal> mealUserMap = mealsByUser.computeIfAbsent(userId, id -> new ConcurrentHashMap<>());
            meal.setId(counter.incrementAndGet());
            mealUserMap.put(meal.getId(), meal);
            return meal;
        }
        Map<Integer, Meal> mealUserMap = mealsByUser.get(userId);
        if (mealUserMap == null) {
            return null;
        }
        return mealUserMap.computeIfPresent(meal.getId(), (id, oldMeal)  -> meal);
    }


    @Override
    public boolean delete(int userId, int mealId) {
        Map<Integer, Meal> mealUserMap = mealsByUser.get(userId);
        if (mealUserMap == null) {
            return false;
        }
        return mealUserMap.remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        Map<Integer, Meal> mealUserMap = mealsByUser.get(userId);
        if (mealUserMap == null) {
            return null;
        }
        return mealUserMap.get(mealId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> mealUserMap = mealsByUser.get(userId);
        if (mealUserMap == null) {
            return Collections.emptyList();
        }
        return mealUserMap.values().stream().
                sorted(Comparator.comparing(Meal::getDate).thenComparing(Meal::getTime).reversed()).
                collect(Collectors.toList());
    }
}