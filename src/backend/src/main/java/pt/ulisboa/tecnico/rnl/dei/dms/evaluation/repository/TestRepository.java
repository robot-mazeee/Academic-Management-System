package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    
}
