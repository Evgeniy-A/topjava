package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(authUserId(), mealId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(authUserId(), mealId);
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        service.update(meal, authUserId());
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info(
                "getBetween Date {} - {} Time {} - {} with id={}",
                startDate, endDate, startTime, endTime, authUserId());
        checkRange(startDate, endDate);
        checkRange(startTime, endTime);
        List<Meal> meals;
        if (startDate == null && endDate == null) {
            meals = service.getAll(authUserId());
        } else {
            meals = service
                    .getBetween(authUserId(), atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate));
        }
        if (startTime == null && endTime == null) {
            return MealsUtil.getTos(meals, authUserCaloriesPerDay());
        }
        return MealsUtil
                .getFilteredTos(meals, authUserCaloriesPerDay(), defaultToMin(startTime), defaultToMax(endTime));
    }
}