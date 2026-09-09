package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL1_ID = START_SEQ + 3;
    public static final int USER_MEAL2_ID = START_SEQ + 4;
    public static final int USER_MEAL3_ID = START_SEQ + 5;
    public static final int USER_MEAL4_ID = START_SEQ + 6;
    public static final int USER_MEAL5_ID = START_SEQ + 7;
    public static final int USER_MEAL6_ID = START_SEQ + 8;
    public static final int USER_MEAL7_ID = START_SEQ + 9;
    public static final int USER_MEAL8_ID = START_SEQ + 10;
    public static final int USER_MEAL9_ID = START_SEQ + 11;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 12;
    public static final int ADMIN_MEAL2_ID = START_SEQ + 13;

    public static final Meal userMeal1 = new Meal(USER_MEAL1_ID,
            LocalDateTime.of(2026, 8, 29, 8, 0),
            "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(USER_MEAL2_ID,
            LocalDateTime.of(2026, 8, 29, 13, 0),
            "Обед", 700);
    public static final Meal userMeal3 = new Meal(USER_MEAL3_ID,
            LocalDateTime.of(2026, 8, 29, 19, 0),
            "Ужин", 600);

    public static final Meal userMeal4 = new Meal(USER_MEAL4_ID,
            LocalDateTime.of(2026, 8, 30, 8, 30),
            "Завтрак", 700);
    public static final Meal userMeal5 = new Meal(USER_MEAL5_ID,
            LocalDateTime.of(2026, 8, 30, 13, 30),
            "Обед", 900);
    public static final Meal userMeal6 = new Meal(USER_MEAL6_ID,
            LocalDateTime.of(2026, 8, 30, 20, 0),
            "Ужин", 800);


    public static final Meal userMeal7 = new Meal(USER_MEAL7_ID,
            LocalDateTime.of(2026, 8, 31, 9, 0),
            "Завтрак", 400);
    public static final Meal userMeal8 = new Meal(USER_MEAL8_ID,
            LocalDateTime.of(2026, 8, 31, 14, 0),
            "Обед", 750);
    public static final Meal userMeal9 = new Meal(USER_MEAL9_ID,
            LocalDateTime.of(2026, 8, 31, 20, 0),
            "Ужин", 650);

    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL1_ID,
            LocalDateTime.of(2026, 8, 30, 9, 0),
            "Завтрак", 500);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL2_ID,
            LocalDateTime.of(2026, 8, 30, 14, 0),
            "Обед", 800);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2026, 1, 1, 1, 1), "test_new", 1);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.of(2026, 2, 2, 2, 2));
        updated.setDescription("test_update");
        updated.setCalories(2);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }
}