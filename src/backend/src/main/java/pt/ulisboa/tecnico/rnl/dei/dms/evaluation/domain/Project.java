package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;

@Getter
@Setter
@Entity
public class Project extends Evaluation {
    @Column(name = "submission_deadline")
    private LocalDateTime submissionDeadline;

    @Column(name = "max_group_size")
    private int maxGroupSize;

    protected Project() {}

    public Project(
        String title, 
        Double weight, 
        CurricularUnit curricularUnit, 
        LocalDateTime submissionDeadline, 
        int maxGroupSize
    ) {
            super(title, weight, curricularUnit);
            this.submissionDeadline = submissionDeadline;
            this.maxGroupSize = maxGroupSize;
    }
}
