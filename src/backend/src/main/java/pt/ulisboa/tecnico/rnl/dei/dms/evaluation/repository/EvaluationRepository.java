package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.JpaRepository;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;

@NoRepositoryBean
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> { }
