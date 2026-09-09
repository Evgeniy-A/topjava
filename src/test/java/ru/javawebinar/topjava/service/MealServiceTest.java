package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL1_ID, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL1_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal meal = MealTestData.getUpdated();
        service.update(meal, USER_ID);
        assertMatch(service.get(meal.getId(), USER_ID), meal);
    }

    @Test
    public void updateNotOwn() {
        Meal meal = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(meal, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Meal newMeal = MealTestData.getNew();
        Integer id = created.getId();
        newMeal.setId(id);
        assertMatch(created, newMeal);
        assertMatch(service.get(id, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        LocalDateTime dateTime = userMeal1.getDateTime();
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, dateTime, "Duplicate", 1), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> actual = service.getBetweenInclusive(
                LocalDate.of(2026, 8, 30),
                LocalDate.of(2026, 8, 31),
                USER_ID);
        assertMatch(actual,
                userMeal9,
                userMeal8,
                userMeal7,
                userMeal6,
                userMeal5,
                userMeal4
        );
    }

    @Test
    public void getAll() {
        assertMatch(
                service.getAll(ADMIN_ID),
                adminMeal2,
                adminMeal1
        );
    }
}