package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.TimeUtil.DATE_TIME_FORMATTER;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage storage;


    @Override
    public void init() throws ServletException {
        storage = new InMemoryMealStorage(MealsUtil.meals);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "update" :
                request.setAttribute("meal", storage.get(Integer.parseInt(request.getParameter("id"))));
            case "add" :
                log.debug("forward to editMeal");
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            case "delete" :
                int id = Integer.parseInt(request.getParameter("id"));
                log.debug("delete meal, id={}", id);
                storage.delete(id);
                response.sendRedirect(request.getRequestURI());
                break;
            case "list":
            default:
                log.debug("forward to meals");
                List<MealTo> mealsTo =  MealsUtil.filteredByStreams(storage.getAll(), CALORIES_PER_DAY, meal -> true);
                request.setAttribute("mealsTo", mealsTo);
                request.setAttribute("dateTimeFormatter", DATE_TIME_FORMATTER);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = ((request.getParameter("id")).trim().isEmpty()) ? null : Integer.parseInt(request.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        storage.save(new Meal(id, dateTime, description, calories));
        response.sendRedirect(request.getRequestURI());
    }
}
