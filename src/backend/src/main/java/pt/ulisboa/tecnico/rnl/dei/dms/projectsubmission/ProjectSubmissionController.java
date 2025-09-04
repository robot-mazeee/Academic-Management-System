package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.ProjectSubmissionDto;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.service.ProjectSubmissionService;

@RestController
public class ProjectSubmissionController {

    @Autowired
    private ProjectSubmissionService submissionService;

    @PostMapping("/submissions")
    public ProjectSubmissionDto createSubmission(@RequestBody ProjectSubmissionDto submissionDto) {
        return submissionService.createProjectSubmission(submissionDto);
    }

    @GetMapping("submissions/project/{projectId}")
    public List<ProjectSubmissionDto> getSubmissionsByProject(@PathVariable long projectId) {
        return submissionService.getProjectSubmissionsByProject(projectId);
    }

    @GetMapping("submissions/student/{personId}")
    public List<ProjectSubmissionDto> getSubmissionsByStudent(@PathVariable long personId) {
        return submissionService.getProjectSubmissionsByStudent(personId);
    }

    @GetMapping("submissions/student/{personId}/project/{projectId}")
    public List<ProjectSubmissionDto> getSubmissionsByStudentAndProject(@PathVariable long personId, @PathVariable long projectId) {
        return submissionService.getProjectSubmissionsByStudentAndProject(personId, projectId);
    }

    @GetMapping("submissions/group/{groupId}")
    public List<ProjectSubmissionDto> getSubmissionsByGroup(@PathVariable long groupId) {
        return submissionService.getProjectSubmissionsByGroup(groupId);
    }
}
