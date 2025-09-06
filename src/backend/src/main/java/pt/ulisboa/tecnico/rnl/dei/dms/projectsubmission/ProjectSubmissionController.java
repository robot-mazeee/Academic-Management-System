package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.file.domain.File;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.ProjectSubmissionDto;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.service.ProjectSubmissionService;

@RestController
public class ProjectSubmissionController {

    @Autowired
    private ProjectSubmissionService submissionService;

    @PostMapping("/project/{projectId}/student/{studentId}")
    public ProjectSubmissionDto createSubmission(@PathVariable Long projectId, @PathVariable Long studentId) {
        return submissionService.createSubmission(projectId, studentId);
    }

    @GetMapping("/submissions/project/{projectId}")
    public List<ProjectSubmissionDto> getSubmissionsByProject(@PathVariable long projectId) {
        return submissionService.getProjectSubmissionsByProject(projectId);
    }

    @GetMapping("/submissions/student/{personId}")
    public List<ProjectSubmissionDto> getSubmissionsByStudent(@PathVariable long personId) {
        return submissionService.getProjectSubmissionsByStudent(personId);
    }

    @GetMapping("/submissions/student/{personId}/project/{projectId}")
    public List<ProjectSubmissionDto> getSubmissionsByStudentAndProject(@PathVariable long personId, @PathVariable long projectId) {
        return submissionService.getProjectSubmissionsByStudentAndProject(personId, projectId);
    }

    @GetMapping("/submissions/group/{groupId}")
    public List<ProjectSubmissionDto> getSubmissionsByGroup(@PathVariable long groupId) {
        return submissionService.getProjectSubmissionsByGroup(groupId);
    }

    @PatchMapping("/project-submissions/{projectId}/submission")
    public ProjectSubmissionDto assignProjectSubmission(@PathVariable Long projectId, @RequestBody String submission) {
        return submissionService.assignSubmission(projectId, submission);
    }

    @GetMapping("/project-submissions/{projectId}/submission")
    public File getSubmission(@PathVariable long projectId) {
        return submissionService.getSubmission(projectId);
    }
}
