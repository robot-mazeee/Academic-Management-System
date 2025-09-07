package pt.ulisboa.tecnico.rnl.dei.dms.revision.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.TestRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.Revision;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.RevisionHistory;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.RevisionStatus;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.dto.RevisionDto;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.dto.RevisionHistoryDto;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.repository.RevisionHistoryRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.repository.RevisionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RevisionService {
    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RevisionHistoryRepository revisionHistoryRepository;

    private Revision fetchRevisionOrThrow(long id) {
        return revisionRepository.findById(id)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_REVISION, Long.toString(id)));
    }

    @Transactional
    public List<RevisionDto> getRevisions() {
        return revisionRepository.findAll().stream()
                .map(RevisionDto::new)
                .toList();
    }

    @Transactional
    public RevisionDto getRevisionByStudentAndTest(long studentId, long testId) {
        return revisionRepository.findByStudent_IdAndTest_Id(studentId, testId)
                .map(RevisionDto::new)
                .orElse(null);
    }

    @Transactional
    public List<RevisionDto> getRevisionByStudent(long studentId) {
        return revisionRepository.findByStudent_Id(studentId).stream()
                .map(RevisionDto::new)
                .toList();
    }

    @Transactional
    public void createRevisionHistory(Revision revision) {
        RevisionHistory history = new RevisionHistory(revision, revision.getStatus());
        revisionHistoryRepository.save(history);
        revision.getHistory().add(history);
    }

    @Transactional
    public RevisionDto createRevision(RevisionDto revisionDto) {
        Test test = testRepository.findById(revisionDto.test().id())
            .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_TEST, Long.toString(revisionDto.test().id())));

        Person student = personRepository.findById(revisionDto.student().id())
            .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PERSON, Long.toString(revisionDto.student().id())));

        Revision revision = new Revision(
            RevisionStatus.valueOf(revisionDto.status().toUpperCase()),
            test,
            student,
            revisionDto.reason()
        );
        revisionRepository.save(revision);

        createRevisionHistory(revision);

        return new RevisionDto(revision);
    }

    @Transactional
    public RevisionDto updateStatus(RevisionDto revisionDto) {
        Revision revision = fetchRevisionOrThrow(revisionDto.id());
        revision.setStatus(RevisionStatus.valueOf(revisionDto.status().toUpperCase()));
        revisionRepository.save(revision);

        createRevisionHistory(revision);

        return new RevisionDto(revision);
    }

    @Transactional
    public long getCountByStatus(RevisionStatus status) {
        return revisionRepository.countByStatus(status);
    }

    @Transactional
    public List<RevisionHistoryDto> getRevisionHistory(long revisionId) {
        Revision revision = fetchRevisionOrThrow(revisionId);

        return revisionHistoryRepository.findByRevisionOrderByDateTimeAsc(revision)
                .stream()
                .map(RevisionHistoryDto::new)
                .toList();
    }
}
