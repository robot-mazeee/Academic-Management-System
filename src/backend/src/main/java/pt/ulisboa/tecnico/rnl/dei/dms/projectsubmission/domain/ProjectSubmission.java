package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.ProjectSubmissionDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;

@Getter
@Setter
@Entity
public class ProjectSubmission {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Group group;

    private LocalDateTime subDateTime;

    // submission

    private double grade;

    public ProjectSubmission() {}

    public ProjectSubmission(Project project, Group group, LocalDateTime subDateTime, double grade) {
        this.project = project;
        this.group = group;
        this.subDateTime = subDateTime;
        this.grade = grade;
    }

    public ProjectSubmission(ProjectSubmissionDto projectSubmissionDto) {
		this(
            projectSubmissionDto.project(),
			projectSubmissionDto.group(), 
            projectSubmissionDto.subDateTime(),
			projectSubmissionDto.grade()
		);
		System.out.println("Project Grade Dto: " + projectSubmissionDto);
	}
}

