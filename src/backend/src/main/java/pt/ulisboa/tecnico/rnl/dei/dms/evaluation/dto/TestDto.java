package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto;

import java.time.LocalDate;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;

public record TestDto(long id, String title, Double weight, long curricularUnitId, LocalDate testdate) {
    public TestDto(Test test) {
        this(
            test.getId(),
            test.getTitle(),
            test.getWeight(),
            test.getCurricularUnit().getId(),
            test.getTestDate()
        );
    }
}