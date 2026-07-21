package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            int calories = userMeal.getCalories();
            int caloriesSumOfDate = caloriesMap.getOrDefault(date, 0);
            caloriesMap.put(date, caloriesSumOfDate + calories);
        }
        List<UserMealWithExcess> filteredUserMealWithExcess = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalTime time = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(time, startTime, endTime)) {
                LocalDateTime dateTime = userMeal.getDateTime();
                String description = userMeal.getDescription();
                int calories = userMeal.getCalories();
                boolean excess = (caloriesMap.get(dateTime.toLocalDate()) - caloriesPerDay > 5);
                filteredUserMealWithExcess.add(new UserMealWithExcess(dateTime, description, calories, excess));
            }
        }
        return filteredUserMealWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Boolean> caloriesMap = meals.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.collectingAndThen(
                        Collectors.summingInt(UserMeal::getCalories), sum -> sum - caloriesPerDay > 5)));
        return meals.stream()
                .filter(userMael -> TimeUtil.isBetweenHalfOpen(userMael.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), caloriesMap.get(userMeal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }
}