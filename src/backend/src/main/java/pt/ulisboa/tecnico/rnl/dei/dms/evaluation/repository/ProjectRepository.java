package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByCurricularUnit(CurricularUnit curricularUnit);
}
