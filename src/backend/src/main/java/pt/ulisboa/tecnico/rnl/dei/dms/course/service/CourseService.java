package pt.ulisboa.tecnico.rnl.dei.dms.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.course.domain.Course;
import pt.ulisboa.tecnico.rnl.dei.dms.course.dto.CourseDto;
import pt.ulisboa.tecnico.rnl.dei.dms.course.repository.CourseRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;

@Service
@Transactional
public class CourseService {
    @Autowired
	private CourseRepository courseRepository;

    private Course fetchCourseOrThrow(long id) {
		return courseRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_COURSE, Long.toString(id)));
	}

    @Transactional
    public List<CourseDto> getCourses() {
        return courseRepository.findAll().stream()
                .map(CourseDto::new)
                .toList();
    }

    @Transactional
	public CourseDto createCourse(CourseDto courseDto) {
		Course course = new Course(courseDto);
		course.setId(null);
		return new CourseDto(courseRepository.save(course));
	}

    @Transactional
	public CourseDto updateCourse(long id, CourseDto courseDto) {
		Course existingCourse = fetchCourseOrThrow(id);
		
		existingCourse.setName(courseDto.name());
		existingCourse.setCode(courseDto.code());
		existingCourse.setDuration(courseDto.duration());

		return new CourseDto(courseRepository.save(existingCourse));
	}

    @Transactional
	public void deleteCourse(long id) {
		fetchCourseOrThrow(id);
		courseRepository.deleteById(id);
	}
}