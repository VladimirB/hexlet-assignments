package exercise.controller;

import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/people")
public class PeopleController {

    // Автоматически заполняем значение поля
    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person getPerson(@PathVariable long id) {
        return this.personRepository.findById(id);
    }

    @GetMapping(path = "")
    public Iterable<Person> getPeople() {
        return this.personRepository.findAll();
    }

    @PostMapping
    public String createPerson(@RequestBody Person person) {
        personRepository.save(person);
        return "OK";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") long id, @RequestBody Person person) {
        person.setId(id);
        personRepository.save(person);
        return "Updated";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") long id) {
        personRepository.deleteById(id);
        return "Deleted";
    }
}
