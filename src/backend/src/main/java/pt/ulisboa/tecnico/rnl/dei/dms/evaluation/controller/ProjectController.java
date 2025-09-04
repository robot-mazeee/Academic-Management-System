package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.service.ProjectService;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.ProjectDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public List<ProjectDto> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/projects/{projectId}")
    public ProjectDto getProject(@PathVariable long projectId) {
        return projectService.getProject(projectId);
    }

    @GetMapping("/curricular-units/{ucId}/projects")
    public List<ProjectDto> getProjectsByUC(@PathVariable Long ucId) {
        return projectService.getProjectsByUc(ucId);
    }

    @PostMapping("curricular-units/{ucId}/projects")
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @PutMapping("projects/{projectId}")
    public void updateProject(@PathVariable long projectId, @RequestBody ProjectDto projectDto) {
        projectService.updateProject(projectId, projectDto);
    }

    @DeleteMapping("projects/{projectId}")
    public void deleteProject(@PathVariable long projectId) {
        projectService.deleteProject(projectId);
    }
}
