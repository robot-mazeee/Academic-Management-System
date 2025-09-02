package pt.ulisboa.tecnico.rnl.dei.dms.testgrade.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.domain.TestGrade;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

public record TestGradeDto(long id, Person student, double grade) {
    public TestGradeDto(TestGrade testGrade) {
        this(
            testGrade.getId(),
            testGrade.getStudent(),
            testGrade.getGrade()
        );
    }
}