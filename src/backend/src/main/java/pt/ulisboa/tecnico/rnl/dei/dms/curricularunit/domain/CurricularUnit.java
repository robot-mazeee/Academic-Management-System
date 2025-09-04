package pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.course.domain.Course;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.dto.CurricularUnitDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "curricular_units")
public class CurricularUnit {
    @Id
	@GeneratedValue
	private Long id;

    @Column(name = "code", nullable = false)
	private String code;

    @Column(name = "name", nullable = false)
	private String name;

    @Column(name = "semester", nullable = false)
	private Integer semester;

	@ManyToOne
	private Course course;

    @ManyToOne
    @JoinColumn(name = "main_teacher")
    private Person mainTeacher;

	@ManyToMany
	@JoinTable(
		name = "uc_students",
		joinColumns = @JoinColumn(name = "uc_id"),
		inverseJoinColumns = @JoinColumn(name = "student_id")
	)
	private List<Person> students = new ArrayList<>();

	@ManyToMany
	@JoinTable(
		name = "uc_teachers",
		joinColumns = @JoinColumn(name = "uc_id"),
		inverseJoinColumns = @JoinColumn(name = "teacher_id")
	)
	private Set<Person> teachingAssistants = new HashSet<>();

    protected CurricularUnit() {
	}

	public CurricularUnit(String code, String name, Integer semester, Course course, Person mainTeacher) {
		this.code = code;
		this.name = name;
		this.semester = semester;
		this.course = course;
        this.mainTeacher = mainTeacher;
	}

	public CurricularUnit(CurricularUnitDto curricularUnitDto) {
		this(
			curricularUnitDto.code(), 
			curricularUnitDto.name(),
			curricularUnitDto.semester(),
			curricularUnitDto.course(),
            curricularUnitDto.mainTeacher()
		);
		System.out.println("CurricularUnitDto: " + curricularUnitDto);
	}
}
