package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.Group;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository.GroupRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.ProjectRepository;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.file.domain.File;
import pt.ulisboa.tecnico.rnl.dei.dms.file.repository.FileRepository;
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

    @Autowired
	private GroupRepository groupRepository;

    @Autowired
	private FileRepository fileRepository;

    private File fetchFileOrThrow(String fileName) {
        return fileRepository.findByName(fileName)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_FILE, fileName));
    }

    private ProjectSubmission fetchProjectSubmissionOrThrow(long id) {
		return projectSubmissionRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PROJECT_SUBMISSION, Long.toString(id)));
	}

    private Project fetchProjectOrThrow(long projectId) {
		return projectRepository.findById(projectId)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PROJECT, Long.toString(projectId)));
	}

    @Transactional
    public ProjectSubmissionDto createSubmission(Long projectId, Long studentId, String submissionFile) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PROJECT, Long.toString(projectId)));

        Group group = groupRepository.findByProjectAndStudent(projectId, studentId);

        ProjectSubmission submission = new ProjectSubmission();
        submission.setProject(project);
        submission.setGroup(group);
        submission.setSubDateTime(LocalDateTime.now());
        submission.setSubmission(submissionFile);
        double randomGrade = ThreadLocalRandom.current().nextDouble(0, 20);
        double roundedGrade = Math.round(randomGrade * 100.0) / 100.0;
        submission.setGrade(roundedGrade);
        System.out.println(submission.getSubmission());

        return new ProjectSubmissionDto(projectSubmissionRepository.save(submission));
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
    public Optional<ProjectSubmissionDto> getStudentLatestSubmission(long personId, long projectId) {
        List<ProjectSubmissionDto> submissions =
                getProjectSubmissionsByStudentAndProject(personId, projectId);

        return submissions.isEmpty()
                ? Optional.empty()
                : Optional.of(submissions.get(submissions.size() - 1));
    }

    @Transactional
    public List<ProjectSubmissionDto> getProjectSubmissionsByGroup(long groupId) {
        List<ProjectSubmission> submissions = projectSubmissionRepository.findByGroup_Id(groupId);
        return submissions.stream().map(ProjectSubmissionDto::new).toList();
    }

    @Transactional
    public List<ProjectSubmissionDto> getLatestProjectSubmissionsByProject(long projectId) {
        List<ProjectSubmission> submissions = projectSubmissionRepository.findAllByProject_Id(projectId);
        return submissions.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getGroup().getId(),
                        Collectors.maxBy(Comparator.comparing(ProjectSubmission::getGrade))
                ))
                .values().stream()
                .flatMap(Optional::stream)
                .map(ProjectSubmissionDto::new)
                .toList();
    }

    @Transactional
    public List<ProjectSubmissionDto> getSubmissionsByProjectAndGroupAsc(long projectId, long groupId) {
        return projectSubmissionRepository
                .findAllByProject_IdAndGroup_IdOrderBySubDateTimeAsc(projectId, groupId)
                .stream()
                .map(ProjectSubmissionDto::new)
                .toList();
    }

    @Transactional
    public Optional<ProjectSubmissionDto> getGroupLatestSubmission(long groupId) {
        List<ProjectSubmissionDto> submissions =
                getProjectSubmissionsByGroup(groupId);

        return submissions.isEmpty()
                ? Optional.empty()
                : Optional.of(submissions.get(submissions.size() - 1));
    }

    @Transactional
    public File getSubmission(Long projectSubmissionId) {
        ProjectSubmission projectSubmission = fetchProjectSubmissionOrThrow(projectSubmissionId);
        String submissionName = projectSubmission.getSubmission();
        return fetchFileOrThrow(submissionName);
    }

    @Transactional
    public ProjectSubmissionDto updateGrade(long submissionId, double grade) {
        ProjectSubmission submission = fetchProjectSubmissionOrThrow(submissionId);
        submission.setGrade(grade);
        return new ProjectSubmissionDto(submission);
    }
}
