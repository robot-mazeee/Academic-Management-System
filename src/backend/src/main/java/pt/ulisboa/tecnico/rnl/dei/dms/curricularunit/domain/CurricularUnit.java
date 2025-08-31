package pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.dto.CurricularUnitDto;

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

    // Later, this is a relationship with CourseDto
    @Column(name = "course", nullable = false)
	private String course;

    @ManyToOne
    @JoinColumn(name = "main_teacher")
    private Person mainTeacher;

    protected CurricularUnit() {
	}

	public CurricularUnit(String code, String name, Integer semester, String course, Person mainTeacher) {
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
