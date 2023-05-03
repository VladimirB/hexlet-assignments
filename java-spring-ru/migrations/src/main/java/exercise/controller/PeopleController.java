package exercise.controller;

import exercise.mapper.PersonMapper;
import exercise.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    JdbcTemplate jdbc;

    private final PersonMapper personMapper = new PersonMapper();

    @PostMapping(path = "")
    public void createPerson(@RequestBody Map<String, Object> person) {
        String query = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        jdbc.update(query, person.get("first_name"), person.get("last_name"));
    }

    // BEGIN
    @GetMapping
    public List<Person> getAll() {
        String query = "SELECT * FROM person";
        return jdbc.query(query, personMapper);
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable("id") int id) {
        String query = "SELECT * FROM person WHERE id=?";
        return jdbc.queryForObject(query, personMapper, id);
    }
    // END
}
