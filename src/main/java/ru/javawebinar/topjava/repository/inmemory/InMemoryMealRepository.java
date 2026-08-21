package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> mealsByUser = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        Map<Integer, Meal> mealUserMap;
        if (meal.isNew()) {
            mealUserMap = getOrCreateMealsByUser(userId);
        } else {
            mealUserMap = getMealsByUser(userId);
            if (mealUserMap == null) {
                return null;
            }
        }
        synchronized (mealUserMap) {
            Meal mealSave = saveIfNew(meal, mealUserMap);
            if(mealSave != null) {
                return mealSave;
            }
            return mealUserMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    private Meal saveIfNew(Meal meal, Map<Integer, Meal> mealUserMap) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealUserMap.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        Map<Integer, Meal> mealUserMap = getMealsByUser(userId);
        if(mealUserMap == null) {
            return false;
        }
        synchronized (mealUserMap) {
            return mealUserMap.remove(mealId) != null;
        }
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
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public Collection<Meal> getBetween(int userId, LocalDateTime start, LocalDateTime end) {
        return filterByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), start, end));
    }

    private Collection<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> mealUserMap = mealsByUser.get(userId);
        if (mealUserMap == null) {
            return Collections.emptyList();
        }
        return mealUserMap.values().stream().filter(filter).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

    private Map<Integer, Meal> getMealsByUser (Integer userId) {
        return mealsByUser.get(userId);
    }

    private Map<Integer, Meal> getOrCreateMealsByUser (Integer userId) {
        return mealsByUser.computeIfAbsent(userId, id -> new ConcurrentHashMap<>());
    }
}