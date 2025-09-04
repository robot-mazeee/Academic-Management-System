package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.ProjectRepository;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;

import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.ProjectSubmission;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.ProjectSubmissionDto;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository.ProjectSubmissionRepository;

@Service
@Transactional
public class ProjectSubmissionService {
	@Autowired
	private ProjectSubmissionRepository projectSubmissionRepository;

    @Autowired
	private ProjectRepository projectRepository;

    private Project fetchProjectOrThrow(long projectId) {
		return projectRepository.findById(projectId)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PROJECT, Long.toString(projectId)));
	}

    @Transactional
	public ProjectSubmissionDto createProjectSubmission(ProjectSubmissionDto projectSubmissionDto) {
        ProjectSubmission projectSubmission = new ProjectSubmission(projectSubmissionDto);
        projectSubmission.setId(null);
        return new ProjectSubmissionDto(projectSubmissionRepository.save(projectSubmission));
	}

    @Transactional
	public List<ProjectSubmissionDto> getProjectSubmissionsByProject(long projectId) {
        Project project = fetchProjectOrThrow(projectId);

		return projectSubmissionRepository.findAllByProject(project).stream()
				.map(ProjectSubmissionDto::new).toList();
	}

    @Transactional
    public List<ProjectSubmissionDto> getProjectSubmissionsByStudent(long personId) {
        List<ProjectSubmission> submissions = projectSubmissionRepository.findByGroup_Members_Id(personId);
        return submissions.stream().map(ProjectSubmissionDto::new).toList();
    }

    @Transactional
    public List<ProjectSubmissionDto> getProjectSubmissionsByStudentAndProject(long personId, long projectId) {
        List<ProjectSubmission> submissions = projectSubmissionRepository.findByProject_IdAndGroup_Members_Id(projectId, personId);
        return submissions.stream().map(ProjectSubmissionDto::new).toList();
    }

    @Transactional
    public List<ProjectSubmissionDto> getProjectSubmissionsByGroup(long groupId) {
        List<ProjectSubmission> submissions = projectSubmissionRepository.findByGroup_Id(groupId);
        return submissions.stream().map(ProjectSubmissionDto::new).toList();
    }
}
