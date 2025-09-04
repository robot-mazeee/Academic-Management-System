package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto;

import java.util.List;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.Group;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.ProjectDto;

public record GroupDto(long id, List<Person> members, ProjectDto projectDto) {
    public GroupDto(Group group) {
        this(
            group.getId(),
            group.getMembers(),
            new ProjectDto(group.getProject())
        );
    }
}
