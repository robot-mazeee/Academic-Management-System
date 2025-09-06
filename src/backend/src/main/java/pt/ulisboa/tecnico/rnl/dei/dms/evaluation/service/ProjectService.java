package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.ProjectRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.repository.CurricularUnitRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.file.domain.File;
import pt.ulisboa.tecnico.rnl.dei.dms.file.repository.FileRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.GroupDto;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository.ProjectSubmissionRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.service.GroupService;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.ProjectDto;

@Transactional
@Service
public class ProjectService {
    @Autowired
	private ProjectRepository projectRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
	private CurricularUnitRepository curricularUnitRepository;

    @Autowired
    private ProjectSubmissionRepository projectSubmissionRepository;

    @Autowired
    private FileRepository fileRepository;

    private CurricularUnit fetchCurricularUnitOrThrow(long curricularUnitId) {
        return curricularUnitRepository.findById(curricularUnitId)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_CURRICULAR_UNIT, Long.toString(curricularUnitId)));
    }

    private Project fetchProjectOrThrow(long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PROJECT, Long.toString(projectId)));
    }

    private File fetchFileOrThrow(String fileName) {
        return fileRepository.findByName(fileName)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_FILE, fileName));
    }

    @Transactional
	public List<ProjectDto> getProjects() {
		return projectRepository.findAll().stream()
				.map(ProjectDto::new)
				.toList();
	}

    @Transactional
	public ProjectDto getProject(long projectId) {
        Project project = fetchProjectOrThrow(projectId);
		return new ProjectDto(project);
	}

    @Transactional
	public ProjectDto createProject(ProjectDto projectDto) {
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(projectDto.curricularUnitId());

        Project project = new Project(projectDto, curricularUnit);
		project.setId(null);
        
        projectRepository.save(project);
        groupService.generateGroups(project);

		return new ProjectDto(project);
	}

    @Transactional
    public List<ProjectDto> getProjectsByUc(long curricularUnitId) {
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(curricularUnitId);

        return projectRepository.findAllByCurricularUnit(curricularUnit).stream()
                .map(ProjectDto::new)
                .toList();
    }
    
    @Transactional
    public GroupDto getGroupByProjectAndStudent(Long projectId, Long studentId) {
        return groupService.getGroupByProjectAndStudent(projectId, studentId);
    }

    @Transactional
    public ProjectDto assignProjectSheet(Long projectId, String projectSheet) {
        Project project = fetchProjectOrThrow(projectId);
        project.setProjectSheet(projectSheet);
        return new ProjectDto(project);
    }

    @Transactional
    public File getProjectSheet(Long projectId) {
        Project project = fetchProjectOrThrow(projectId);
        String projectSheetName = project.getProjectSheet();
        return fetchFileOrThrow(projectSheetName);
    }

    @Transactional
	public ProjectDto updateProject(long projectId, ProjectDto projectDto) {
		Project existingProject = fetchProjectOrThrow(projectId);
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(projectDto.curricularUnitId());
		
		existingProject.setTitle(projectDto.title());
		existingProject.setWeight(projectDto.weight());
		existingProject.setCurricularUnit(curricularUnit);
        existingProject.setDeadline(projectDto.deadline());
        existingProject.setMaxGroupSize(projectDto.maxGroupSize());

		return new ProjectDto(projectRepository.save(existingProject));
	}

    @Transactional
	public void deleteProject(long projectId) {
		fetchProjectOrThrow(projectId);
        projectSubmissionRepository.deleteByProject_Id(projectId);

		projectRepository.deleteById(projectId);
	}
}
