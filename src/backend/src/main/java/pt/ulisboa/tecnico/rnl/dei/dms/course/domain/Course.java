package pt.ulisboa.tecnico.rnl.dei.dms.course.domain;

import jakarta.persistence.*;
import lombok.Data;

import pt.ulisboa.tecnico.rnl.dei.dms.course.dto.CourseDto;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
	@GeneratedValue
	private Long id;

	private String name;

    private String code;

    private int duration;

    protected Course() {}

    public Course(String name, String code, int duration) {
        this.name = name;
        this.code = code;
        this.duration = duration;
    }

    public Course(CourseDto courseDto) {
        this(
            courseDto.name(),
            courseDto.code(),
            courseDto.duration()
        );

        System.out.println("Course Dto: " + courseDto);
    }
}