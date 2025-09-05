package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.Group;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByProject(Project project);
    @Query("""
        SELECT g FROM Group g
        JOIN g.members m
        WHERE g.project.id = :projectId
        AND m.id = :studentId
    """)
    Group findByProjectAndStudent(@Param("projectId") Long projectId, @Param("studentId") Long studentId);
}