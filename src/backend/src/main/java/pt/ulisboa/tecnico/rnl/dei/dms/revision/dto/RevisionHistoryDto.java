package pt.ulisboa.tecnico.rnl.dei.dms.revision.dto;

import java.time.LocalDateTime;

import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.TestDto;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.RevisionHistory;

public record RevisionHistoryDto(long id, PersonDto student, TestDto test, String status, LocalDateTime dateTime) {
    public RevisionHistoryDto(RevisionHistory history) {
        this(
            history.getId(),
            new PersonDto(history.getRevision().getStudent()),
            new TestDto(history.getRevision().getTest()),
            history.getStatus().toString(),
            history.getDateTime()
        );
    }
}
