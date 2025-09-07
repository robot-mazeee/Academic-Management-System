package pt.ulisboa.tecnico.rnl.dei.dms.revision.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.Revision;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.domain.RevisionHistory;

@Repository
public interface RevisionHistoryRepository extends JpaRepository<RevisionHistory, Long> {
    List<RevisionHistory> findByRevisionOrderByDateTimeAsc(Revision revision);
}