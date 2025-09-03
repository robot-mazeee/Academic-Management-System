package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.repository.CurricularUnitRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;

@Transactional
@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CurricularUnitRepository curricularUnitRepository;

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

		Enrollment enrollment = new Enrollment(enrollmentDto, curricularUnit);
		enrollment.setId(null);
		return new EnrollmentDto(enrollmentRepository.save(enrollment));
	}

    @Transactional
	public List<EnrollmentDto> getEnrollmentsByCurricularUnit(long curricularUnitId) {
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(curricularUnitId);

		return (enrollmentRepository.findAllByCurricularUnit(curricularUnit)).stream()
				.map(EnrollmentDto::new)
				.toList();
	}

    @Transactional
	public void deleteEnrollment(long id) {
		fetchEnrollmentOrThrow(id);

		enrollmentRepository.deleteById(id);
	}
}
