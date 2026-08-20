package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            MealService mealService = appCtx.getBean(MealService.class);

            System.out.println("test getAll():");
            for (MealTo mealTo : mealRestController.getAll()) {
                System.out.println(mealTo);
            }

            System.out.println("test getBetween():");
            for (MealTo mealTo : mealRestController.getBetween(
                    LocalDate.of(2020, Month.JANUARY, 29),
                    LocalDate.of(2020, Month.JANUARY, 31),
                    LocalTime.of(9, 0),
                    LocalTime.of(12, 0)
            )) {
                System.out.println(mealTo);
            }

            System.out.println("test get foreign meal:");
            Meal meal0 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
            mealService.create(meal0, 2);
            try {
                mealRestController.get(meal0.getId());
            } catch (NotFoundException e) {
                System.out.println(e);
            }

            System.out.println("test update foreign meal:");
            Meal meal1 = new Meal(meal0.getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
            try {
                mealRestController.update(meal1, meal0.getId());
            } catch (NotFoundException e) {
                System.out.println(e);
            }
        }
    }
}
