package exercise.controller;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.PredicateOperation;
import com.querydsl.core.types.dsl.BooleanExpression;
import exercise.model.User;
import exercise.model.QUser;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public Iterable<User> getUsers(@RequestParam(value = "firstName", required = false) String firstName,
                               @RequestParam(value = "lastName", required = false) String lastName) {
        if (firstName == null && lastName == null) {
            return userRepository.findAll();
        }

        var firstNameExp = firstName != null ? QUser.user.firstName.containsIgnoreCase(firstName) : null;
        var lastNameExp = lastName != null ? QUser.user.lastName.containsIgnoreCase(lastName) : null;

        Predicate predicate;
        if (firstNameExp != null && lastNameExp != null) {
            predicate = firstNameExp.and(lastNameExp);
        } else if (firstNameExp != null) {
            predicate = firstNameExp;
        } else {
            predicate = lastNameExp;
        }
        return userRepository.findAll(predicate);
    }
}

