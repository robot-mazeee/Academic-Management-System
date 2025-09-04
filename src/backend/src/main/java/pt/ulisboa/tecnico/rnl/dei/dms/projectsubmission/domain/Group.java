package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.GroupDto;

@Getter
@Setter
@Entity
public class Group {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private List<Person> members;

    @ManyToOne
    private Project project;

    public Group() {}

    public Group(List<Person> members, Project project) {
        this.members = members;
        this.project = project;
    }

    public Group(GroupDto groupDto) {
        this(
            groupDto.members(),
            groupDto.project()
        );
        System.out.println("Group Dto: " + groupDto);
    }
}
