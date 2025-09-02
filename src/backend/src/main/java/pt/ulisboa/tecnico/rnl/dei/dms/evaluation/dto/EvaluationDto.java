package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;

public record EvaluationDto(long id, String title, Double weight, CurricularUnit curricularUnit) {
    public EvaluationDto(Evaluation evaluation) {
        this(
            evaluation.getId(),
            evaluation.getTitle(),
            evaluation.getWeight(),
            evaluation.getCurricularUnit()
        );
    }
}
