package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.ProjectSubmission;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;

@Repository
public interface ProjectSubmissionRepository extends JpaRepository<ProjectSubmission, Long> {
    List<ProjectSubmission> findAllByProject(Project project);
    List<ProjectSubmission> findByGroup_Members_Id(Long personId);
    List<ProjectSubmission> findByProject_IdAndGroup_Members_Id(Long projectId, Long personId);
    List<ProjectSubmission> findByGroup_Id(Long groupId);
}
