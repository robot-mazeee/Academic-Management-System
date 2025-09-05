package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository.ProjectSubmissionRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.repository.TestGradeRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.EvaluationRepository;

@Service
public class EvaluationService {

    @Autowired
    private ProjectSubmissionRepository projectSubmissionRepository;

    @Autowired
    private TestGradeRepository testGradeRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional
    public void deleteEvaluation(Long evaluationId) {
        projectSubmissionRepository.deleteByProject_Id(evaluationId);
        testGradeRepository.deleteByTest_Id(evaluationId);

        evaluationRepository.deleteById(evaluationId);
    }
}

