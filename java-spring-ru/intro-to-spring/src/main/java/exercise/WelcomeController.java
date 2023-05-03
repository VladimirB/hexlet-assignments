package exercise;

import org.springframework.web.bind.annotation.*;

// BEGIN
@RestController
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public String welcome() {
        return "Welcome to Spring";
    }

    @GetMapping("hello")
    @ResponseBody
    public String hello(@RequestParam(required = false) String name) {
        return name != null ? "Hello, " + name : "Hello, World";
    }
}
// END
