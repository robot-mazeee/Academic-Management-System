package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.ProjectRepository;

@Transactional
@Service
public class ProjectService {
    @Autowired
	private ProjectRepository projectRepository;
}
