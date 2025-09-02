package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto;

import java.time.LocalDateTime;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;

public record ProjectDto(long id, String title, Double weight, long curricularUnitId, LocalDateTime submissionDeadline, int maxGroupSize) {
    public ProjectDto(Project project) {
        this(
            project.getId(),
            project.getTitle(),
            project.getWeight(),
            project.getCurricularUnit().getId(),
            project.getSubmissionDeadline(),
            project.getMaxGroupSize()
        );
    } 
}
