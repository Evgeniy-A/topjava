package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealStorage implements MealStorage{
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    public InMemoryMealStorage(List<Meal> meals) {
        for (Meal meal : meals) {
            save(meal);
        }
    }

    public Map<Integer, Meal> getStorage() {
        return storage;
    }

    @Override
    public Meal save(Meal meal) {
        int id = idCounter.getAndIncrement();
        Meal newMeal = new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        storage.put(id, newMeal);
        return newMeal;
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public boolean update(Meal meal) {
        return (storage.replace(meal.getId(),
                new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories())) != null);
    }

    @Override
    public boolean delete(int id) {
        return (storage.remove(id) != null);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<Meal>(storage.values());
    }
}
