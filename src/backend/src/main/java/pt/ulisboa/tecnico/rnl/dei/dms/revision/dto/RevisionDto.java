package pt.ulisboa.tecnico.rnl.dei.dms.revision.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.TestDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.Revision;

public record RevisionDto(long id, String status, TestDto test, PersonDto student, String reason) {
    public RevisionDto(Revision revision) {
        this(
            revision.getId(),
            revision.getStatus().toString(),
            new TestDto(revision.getTest()),
            new PersonDto(revision.getStudent()),
            revision.getReason()
        );
    }
}
