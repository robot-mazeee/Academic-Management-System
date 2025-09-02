package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.mapper;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.ProjectDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.TestDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;

public class EvaluationMapper {
    public static TestDto toTestDto(Test test) {
        return new TestDto(
            test.getId(),
            test.getTitle(),
            test.getWeight(),
            test.getCurricularUnit().getId(),
            test.getTestDate()
        );
    }

    public static ProjectDto toProjectDto(Project project) {
        return new ProjectDto(
            project.getId(),
            project.getTitle(),
            project.getWeight(),
            project.getCurricularUnit().getId(),
            project.getSubmissionDeadline(),
            project.getMaxGroupSize()
        );
    }
}

