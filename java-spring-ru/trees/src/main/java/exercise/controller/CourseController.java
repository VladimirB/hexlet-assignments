package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCourses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    @GetMapping("/{id}/previous")
    public Iterable<Course> getPrevious(@PathVariable("id") long id) {
        var course = courseRepository.findById(id);
        var ids = getCourseIdsFromPath(course.getPath());
        return courseRepository.findAllById(ids);
    }

    private List<Long> getCourseIdsFromPath(String path) {
        var ids = path.split("\\.");
        return Arrays.stream(ids)
                .map(Long::parseLong)
                .toList();
    }
}
