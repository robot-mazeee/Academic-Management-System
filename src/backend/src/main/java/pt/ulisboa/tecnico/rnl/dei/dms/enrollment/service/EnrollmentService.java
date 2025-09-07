package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.repository.CurricularUnitRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.service.CurricularUnitService;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.service.EvaluationService;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.dto.ProjectSubmissionDto;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.service.ProjectSubmissionService;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.dto.TestGradeDto;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.service.TestGradeService;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.EnrollmentStatus;

@Transactional
@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CurricularUnitRepository curricularUnitRepository;

    @Autowired
    private CurricularUnitService curricularUnitService;

    @Autowired
    private TestGradeService testGradeService;

    @Autowired
    private ProjectSubmissionService projectSubmissionService;

    @Autowired
    private EvaluationService evaluationService;

    private CurricularUnit fetchCurricularUnitOrThrow(long curricularUnitId) {
        return curricularUnitRepository.findById(curricularUnitId)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_CURRICULAR_UNIT, Long.toString(curricularUnitId)));
    }

    private Enrollment fetchEnrollmentOrThrow(long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_ENROLLMENT, Long.toString(enrollmentId)));
    }

    @Transactional
	public EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto) {
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(enrollmentDto.curricularUnitId());
        curricularUnitService.addCurricularUnitStudent(curricularUnit.getId(), enrollmentDto.student().getId());

		Enrollment enrollment = new Enrollment(enrollmentDto, curricularUnit);
		enrollment.setId(null);
		return new EnrollmentDto(enrollmentRepository.save(enrollment));
	}

    @Transactional
	public List<EnrollmentDto> getEnrollmentsByCurricularUnit(long curricularUnitId) {
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(curricularUnitId);

		List<Enrollment> enrollments = (enrollmentRepository.findAllByCurricularUnit(curricularUnit));
        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();
        
        for (Enrollment enrollment : enrollments) {
            enrollmentDtos.add(setEnrollmentFinalGrade(enrollment));
        }
        return enrollmentDtos;
	}

    @Transactional
    public Enrollment getEnrollmentByStudentAndCurricularUnit(long studentId, long curricularUnitId) {
        return enrollmentRepository.findByStudent_IdAndCurricularUnit_Id(studentId, curricularUnitId)
            .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_ENROLLMENT));
    }

    @Transactional
    public List<Enrollment> findByCurricularUnitId(long curricularUnitId) {
        return enrollmentRepository.findByCurricularUnit_Id(curricularUnitId);
    }

    @Transactional
    public EnrollmentDto adjustEnrollmentFinalGrade(long enrollmentId, double finalGrade) {
        Enrollment enrollment = fetchEnrollmentOrThrow(enrollmentId);
        enrollment.setFinalGrade(finalGrade);
        return new EnrollmentDto(enrollment);
    }

    @Transactional
    public EnrollmentDto setEnrollmentFinalGrade(Enrollment enrollment) {
        long curricularUnitId = enrollment.getCurricularUnit().getId();
        List<Evaluation> evaluations = evaluationService.getAllByCurricularUnitId(curricularUnitId);

        double finalGrade = 0;
        for (Evaluation e: evaluations) {
            if (e instanceof Test) {
                Optional<TestGradeDto> testGradeOpt =
                        testGradeService.getTestGradeByStudentByTest(enrollment.getStudent().getId(), e.getId());

                double grade = testGradeOpt
                        .map(tg -> tg.grade() * e.getWeight() / 100)
                        .orElse(0.0);

                finalGrade += grade;
            }
            else if (e instanceof Project) {
                Optional<ProjectSubmissionDto> projectSubmissionOpt =
                        projectSubmissionService.getStudentLatestSubmission(enrollment.getStudent().getId(), e.getId());

                double grade = projectSubmissionOpt
                        .map(ps -> ps.grade() * e.getWeight() / 100)
                        .orElse(0.0);

                finalGrade += grade;
            }
        }

        enrollment.setFinalGrade(finalGrade);

        if (enrollment.getFinalGrade() >= 9.5) {
            enrollment.setStatus(EnrollmentStatus.APPROVED);
        } else {
            enrollment.setStatus(EnrollmentStatus.FAILED);
        }

        return new EnrollmentDto(enrollment);
    }

    @Transactional
	public void deleteEnrollment(long enrollmentId) {
		Enrollment enrollment = fetchEnrollmentOrThrow(enrollmentId);
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(enrollment.getCurricularUnit().getId());
        curricularUnitService.removeCurricularUnitStudent(curricularUnit.getId(), enrollment.getStudent().getId());

		enrollmentRepository.deleteById(enrollmentId);
	}
}
