package pt.ulisboa.tecnico.rnl.dei.dms.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.course.dto.CourseDto;
import pt.ulisboa.tecnico.rnl.dei.dms.course.repository.CourseRepository;

@Service
@Transactional
public class CourseService {
    @Autowired
	private CourseRepository courseRepository;

    @Transactional
    public List<CourseDto> getCourses() {
        return courseRepository.findAll().stream()
                .map(CourseDto::new)
                .toList();
    }
}