package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> { 
    void deleteByCurricularUnitId(long curricularUnitId);
    List<Evaluation> findByCurricularUnit_Id(long curricularUnitId);
}
