package pt.ulisboa.tecnico.rnl.dei.dms.testgrade.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.domain.TestGrade;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

public record TestGradeDto(long id, Test test, Person student, double grade, String correction) {
    public TestGradeDto(TestGrade testGrade) {
        this(
            testGrade.getId(),
            testGrade.getTest(),
            testGrade.getStudent(),
            testGrade.getGrade(),
            testGrade.getCorrection()
        );
    }
}