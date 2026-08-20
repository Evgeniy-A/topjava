package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
       return repository.save(meal, userId);
    }

    public void delete(int userId, int mealId) {
        checkNotFound(repository.delete(userId, mealId), mealId);
    }

    public Meal get(int userId, int mealId) {
        return checkNotFound(repository.get(userId, mealId), mealId);
    }

    public List<Meal> getBetween(int userId, LocalDateTime start, LocalDateTime end) {
        return new ArrayList<>(repository.getBetween(userId, start, end));
    }

    public List<Meal> getAll(int userId) {
        return new ArrayList<>(repository.getAll(userId));
    }

    public Meal update (Meal meal, int userId) {
        return checkNotFound(repository.save(meal, userId), meal.getId());
    }
}