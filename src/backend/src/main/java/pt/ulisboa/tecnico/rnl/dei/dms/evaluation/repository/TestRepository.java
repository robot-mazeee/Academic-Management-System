package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.TestDto;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<TestDto> findAllByCurricularUnit(long curricularUnitId);
}
