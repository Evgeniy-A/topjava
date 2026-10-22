package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String getMeals(Model model) {
        log.info("meals");
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping(value = "/meals/filter")
    public String filterMeals(HttpServletRequest request, Model model) {
        log.info("/meals/filter");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals",
                getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping(value = "/meals/create")
    public String createMeal(Model model) {
        log.info("/meals/create");
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(value = "/meals/update")
    public String updateMeal(HttpServletRequest request, Model model) {
        log.info("/meals/update");
        int id = getId(request);
        Meal meal = get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(value = "/meals/delete")
    public String deleteMeal(HttpServletRequest request) {
        log.info("/meals/delete");
        delete(getId(request));
        return "redirect:/meals";
    }

    @PostMapping("/meals")
    public String setMeal(HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("post /meals");
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.hasLength(request.getParameter("id"))) {
            int id = (getId(request));
            meal.setId(id);
            update(meal, id);
        } else create(meal);
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}