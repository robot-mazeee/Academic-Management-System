package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.ProjectDto;

@Getter
@Setter
@Entity
public class Project extends Evaluation {
    @Column(name = "submission_deadline")
    private LocalDate submissionDeadline;

    @Column(name = "max_group_size")
    private int maxGroupSize;

    protected Project() {}

    public Project(
        String title, 
        Double weight, 
        CurricularUnit curricularUnit, 
        LocalDate submissionDeadline, 
        int maxGroupSize
    ) {
            super(title, weight, curricularUnit);
            this.submissionDeadline = submissionDeadline;
            this.maxGroupSize = maxGroupSize;
    }

    public Project(ProjectDto projectDto, CurricularUnit curricularUnit) {
		this(
			projectDto.title(), 
			projectDto.weight(),
			curricularUnit,
            projectDto.submissionDeadline(),
            projectDto.maxGroupSize()
		);
		System.out.println("Project Dto: " + projectDto);
	}
}
