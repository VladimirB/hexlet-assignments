package exercise;

import exercise.daytimes.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    Meal meal;

    @Autowired
    Daytime daytime;

    @GetMapping("/daytime")
    public String getDaytime() {
        var daytimeName = daytime.getName();
        return String.format("It is %s now. Enjoy your %s",
                daytimeName,
                meal.getMealForDaytime(daytimeName));
    }
}