package pt.ulisboa.tecnico.rnl.dei.dms.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.dms.course.dto.CourseDto;
import pt.ulisboa.tecnico.rnl.dei.dms.course.service.CourseService;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public List<CourseDto> getCourses() {
        return courseService.getCourses();
    }
}
