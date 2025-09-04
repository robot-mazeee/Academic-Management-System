package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.GroupDto;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.service.GroupService;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("groups/project/{projectId}/generate-groups")
    public List<GroupDto> generateGroups(@PathVariable long projectId) {
        Project project = groupService.fetchProjectOrThrow(projectId);
        return groupService.generateGroups(project);
    }

    @GetMapping("groups/project/{projectId}")
    public List<GroupDto> getGroupsByProject(@PathVariable long projectId) {
        return groupService.getGroupsByProject(projectId);
    }
}

