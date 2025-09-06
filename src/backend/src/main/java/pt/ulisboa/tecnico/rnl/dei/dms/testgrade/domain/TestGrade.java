package pt.ulisboa.tecnico.rnl.dei.dms.testgrade.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.dto.TestGradeDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;

@Getter
@Setter
@Entity
public class TestGrade {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Test test;

    @ManyToOne
    private Person student;

    private double grade;

    private String correction;

    protected TestGrade() {}

    public TestGrade(Test test, Person student, double grade) {
        this.test = test;
        this.student = student;
        this.grade = grade;
    }

    public TestGrade(TestGradeDto testGradeDto) {
		this(
            testGradeDto.test(),
			testGradeDto.student(), 
			testGradeDto.grade()
		);
		System.out.println("Test Grade Dto: " + testGradeDto);
	}
}
