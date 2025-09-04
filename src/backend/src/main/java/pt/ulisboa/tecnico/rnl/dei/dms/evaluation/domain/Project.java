package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

import java.time.LocalDate;
import java.util.List;

import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.Group;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.ProjectDto;

@Getter
@Setter
@Entity
public class Project extends Evaluation {
    @Column(name = "submission_deadline")
    private LocalDate deadline;

    @Column(name = "max_group_size")
    private int maxGroupSize;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups;

    protected Project() {}

    public Project(
        String title, 
        Double weight, 
        CurricularUnit curricularUnit, 
        LocalDate deadline, 
        int maxGroupSize
    ) {
            super(title, weight, curricularUnit);
            this.deadline = deadline;
            this.maxGroupSize = maxGroupSize;
    }

    public Project(ProjectDto projectDto, CurricularUnit curricularUnit) {
		this(
			projectDto.title(), 
			projectDto.weight(),
			curricularUnit,
            projectDto.deadline(),
            projectDto.maxGroupSize()
		);
		System.out.println("Project Dto: " + projectDto);
	}
}
