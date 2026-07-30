package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import ru.javawebinar.topjava.model.UserMealWithExcess.DayCaloriesFlag;

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
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 401)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByOnePass(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calories = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            calories.put(date, calories.getOrDefault(date, 0) + userMeal.getCalories());
        }
        List<UserMealWithExcess> filteredUserMealWithExcess = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                LocalDateTime dateTime = userMeal.getDateTime();
                filteredUserMealWithExcess.add(new UserMealWithExcess(
                        dateTime, userMeal.getDescription(), userMeal.getCalories(),
                        (calories.get(dateTime.toLocalDate()) > caloriesPerDay)));
            }
        }
        return filteredUserMealWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Boolean> excessByDate = meals.stream()
                .collect(Collectors.groupingBy(
                        userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.collectingAndThen(
                                Collectors.summingInt(UserMeal::getCalories), sum -> sum > caloriesPerDay)));
        return meals.stream()
                .filter(userMael -> TimeUtil.isBetweenHalfOpen(
                        userMael.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(
                        userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), excessByDate.get(userMeal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByOnePass(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, DayCaloriesFlag> dateFlags = new HashMap<>();
        Map<LocalDate, Integer> calories = new HashMap<>();
        List<UserMealWithExcess> filteredUserMealWithExcess = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            calories.put(date, calories.getOrDefault(date, 0) + userMeal.getCalories());
            DayCaloriesFlag flag = dateFlags.computeIfAbsent(date, v -> new DayCaloriesFlag());
            dateFlags.get(date).setExcess(calories.get(date) > caloriesPerDay);
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredUserMealWithExcess.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        flag));
            }
        }
        return filteredUserMealWithExcess;
    }
}