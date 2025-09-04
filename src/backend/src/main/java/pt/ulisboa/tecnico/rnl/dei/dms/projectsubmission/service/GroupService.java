package pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository.GroupRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.domain.Group;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.GroupDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.ProjectRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

@Service
@Transactional
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
	private ProjectRepository projectRepository;

    public Project fetchProjectOrThrow(long projectId) {
		return projectRepository.findById(projectId)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PROJECT, Long.toString(projectId)));
	}

    public List<GroupDto> generateGroups(Project project) {
        int maxGroupSize = project.getMaxGroupSize();
        List<Person> students = project.getCurricularUnit().getStudents();

        Collections.shuffle(students);

        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < students.size(); i += maxGroupSize) {
            int end = Math.min(i + maxGroupSize, students.size());
            List<Person> groupMembers = students.subList(i, end);

            Group group = new Group();
            group.setProject(project);
            group.setMembers(new ArrayList<>(groupMembers));
            groups.add(group);
        }

        return groupRepository.saveAll(groups).stream()
                    .map(GroupDto::new).toList();
    }

    @Transactional
	public List<GroupDto> getGroupsByProject(long projectId) {
        Project project = fetchProjectOrThrow(projectId);

		return groupRepository.findAllByProject(project).stream()
				.map(GroupDto::new).toList();
	}
}
