package pt.ulisboa.tecnico.rnl.dei.dms.course.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.course.domain.Course;

public record CourseDto(long id, String name, String code, int duration) {
	public CourseDto(Course course) {
		this(
			course.getId(), 
			course.getName(), 
			course.getCode(),
            course.getDuration()
		);
	}
}
