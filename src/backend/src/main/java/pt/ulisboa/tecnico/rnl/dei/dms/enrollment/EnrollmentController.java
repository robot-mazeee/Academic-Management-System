package pt.ulisboa.tecnico.rnl.dei.dms.enrollment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.EnrollmentStatus;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.service.EnrollmentService;

@RestController
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/curricular-units/{curricularUnitId}/enrollments")
    public List<EnrollmentDto> getEnrollmentsByCurricularUnit(@PathVariable long curricularUnitId) {
        return enrollmentService.getEnrollmentsByCurricularUnit(curricularUnitId);
    }

    @GetMapping("/enrollments/{studentId}")
    public List<EnrollmentDto> getEnrollmentsByStudent(@PathVariable long studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    @GetMapping("/enrollments/enrolled")
    public long getEnrolled() {
        return enrollmentService.getCountByStatus(EnrollmentStatus.ENROLLED);
    }

    @GetMapping("/enrollments/approved")
    public long getApproved() {
        return enrollmentService.getCountByStatus(EnrollmentStatus.APPROVED);
    }

    @GetMapping("/enrollments/failed")
    public long getFailed() {
        return enrollmentService.getCountByStatus(EnrollmentStatus.FAILED);
    }

    @PostMapping("/curricular-units/{curricularUnitId}/enrollments")
    public EnrollmentDto createEnrollment(@PathVariable long curricularUnitId, @RequestBody EnrollmentDto enrollmentDto) {
        return enrollmentService.createEnrollment(enrollmentDto);
    }

    @PatchMapping("/curricular-units/{curricularUnitId}/enrollments/{enrollmentId}") 
    public EnrollmentDto adjustEnrollmentFinalGrade(@PathVariable long enrollmentId, double finalGrade) {
        return enrollmentService.adjustEnrollmentFinalGrade(enrollmentId, finalGrade);
    }

    @DeleteMapping("/enrollments/{enrollmentId}")
    public void deleteEnrollment(@PathVariable long enrollmentId) {
        enrollmentService.deleteEnrollment(enrollmentId);
    }
}
