package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.EnrollmentStatus;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findAllByCurricularUnit(CurricularUnit curricularUnit);
    List<Enrollment> findByCurricularUnit_Id(Long curricularUnitId);
    List<Enrollment> findAllByStudent_Id(long studenId);
    Optional<Enrollment> findByStudent_IdAndCurricularUnit_Id(long studentId, long curricularUnitId);
    long countByStatus(EnrollmentStatus status);
}

