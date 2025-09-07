package pt.ulisboa.tecnico.rnl.dei.dms.revision.domain;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.dto.RevisionDto;

@Getter
@Setter
@Entity
public class Revision {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "revision_status")
    private RevisionStatus status;

    @ManyToOne
    private Test test;

    @ManyToOne
    private Person student;

    @NotBlank(message = "Campo IstId é obrigatório")
    @Size(min = 10, max = 100, message = "Justificação tem de ser 10-100 charateres")
    private String reason;

    @OneToMany(mappedBy = "revision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RevisionHistory> history = new ArrayList<>();

    protected Revision() {}

    public Revision(RevisionStatus status, Test test, Person student, String reason) {
        this.status = status;
        this.test = test;
        this.student = student;
        this.reason = reason;
    }

    public Revision(RevisionDto workflowDto, Test test, Person student) {
        this(
            RevisionStatus.valueOf(workflowDto.status().toUpperCase()),
            test,
            student,
            workflowDto.reason()
        );

        System.out.println("Revision Workflow Dto " + workflowDto);
    }
}
