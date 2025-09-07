package pt.ulisboa.tecnico.rnl.dei.dms.revision.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.Revision;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.RevisionStatus;

@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long> {
    Optional<Revision> findByStudent_IdAndTest_Id(long studentId, long testId);
    List<Revision> findByStudent_Id(long studentId);
    long countByStatus(RevisionStatus status);
}
