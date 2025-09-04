package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto;

import java.time.LocalDateTime;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.ProjectSubmission;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.Group;

public record ProjectSubmissionDto(long id, Project project, Group group, LocalDateTime subDateTime, double grade) {
    public ProjectSubmissionDto(ProjectSubmission projectSubmission) {
        this(
            projectSubmission.getId(),
            projectSubmission.getProject(),
            projectSubmission.getGroup(),
            projectSubmission.getSubDateTime(),
            projectSubmission.getGrade()
        );
    }
}