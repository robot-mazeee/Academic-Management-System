package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto;

import java.time.LocalDate;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;

public record ProjectDto(
        long id, 
        String title, 
        Double weight, 
        long curricularUnitId, 
        LocalDate deadline, 
        int maxGroupSize,
        String projectSheet
    ) {
    public ProjectDto(Project project) {
        this(
            project.getId(),
            project.getTitle(),
            project.getWeight(),
            project.getCurricularUnit().getId(),
            project.getDeadline(),
            project.getMaxGroupSize(),
            project.getProjectSheet()
        );
    } 
}
