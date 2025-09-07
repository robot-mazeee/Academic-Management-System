package pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;

// Repository interface for managing CurricularUnit entities
@Repository
@Transactional
public interface CurricularUnitRepository extends JpaRepository<CurricularUnit, Long> {
    List<CurricularUnit> findAllByCourse_Id(long courseId);
}