package pt.ulisboa.tecnico.rnl.dei.dms.testgrade.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.dto.TestGradeDto;

@Getter
@Setter
@Entity
public class TestGrade {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Person student;

    private double grade;

    // correction

    protected TestGrade() {}

    public TestGrade(Person student, double grade) {
        this.student = student;
        this.grade = grade;
    }

    public TestGrade(TestGradeDto testGradeDto) {
		this(
			testGradeDto.student(), 
			testGradeDto.grade()
		);
		System.out.println("Test Grade Dto: " + testGradeDto);
	}
}
