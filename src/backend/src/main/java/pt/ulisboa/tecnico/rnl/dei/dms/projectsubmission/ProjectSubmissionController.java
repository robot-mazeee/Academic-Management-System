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
    public ProjectSubmissionDto createSubmission(@PathVariable Long projectId, @PathVariable Long studentId, @RequestBody String submission) {
        return submissionService.createSubmission(projectId, studentId, submission);
    }

    @GetMapping("/submissions/project/{projectId}")
    public List<ProjectSubmissionDto> getSubmissionsByProject(@PathVariable long projectId) {
        return submissionService.getProjectSubmissionsByProject(projectId);
    }

    @GetMapping("/submissions/student/{studentId}")
    public List<ProjectSubmissionDto> getSubmissionsByStudent(@PathVariable long studentId) {
        return submissionService.getProjectSubmissionsByStudent(studentId);
    }

    @GetMapping("/submissions/student/{personId}/project/{projectId}")
    public List<ProjectSubmissionDto> getSubmissionsByStudentAndProject(@PathVariable long personId, @PathVariable long projectId) {
        return submissionService.getProjectSubmissionsByStudentAndProject(personId, projectId);
    }

    @GetMapping("/submissions/group/{groupId}")
    public List<ProjectSubmissionDto> getSubmissionsByGroup(@PathVariable long groupId) {
        return submissionService.getProjectSubmissionsByGroup(groupId);
    }

    @GetMapping("/submissions/project/{projectId}/latest")
    public List<ProjectSubmissionDto> getLatestProjectSubmissionsByProject(@PathVariable long projectId) {
        return submissionService.getLatestProjectSubmissionsByProject(projectId);
    }

    @GetMapping("/submissions/project/{projectId}/groups/{groupId}")
    public List<ProjectSubmissionDto> getLatestProjectSubmissionsByProject(@PathVariable long projectId, @PathVariable long groupId) {
        return submissionService.getSubmissionsByProjectAndGroupAsc(projectId, groupId);
    }

    @GetMapping("/project-submissions/{projectId}/submission")
    public File getSubmission(@PathVariable long projectId) {
        return submissionService.getSubmission(projectId);
    }

    @PatchMapping("/project-submissions/{submissionId}/grade")
    public ProjectSubmissionDto updateGrade(@PathVariable long submissionId, @RequestBody double grade) {
        return submissionService.updateGrade(submissionId, grade);
    }
}
