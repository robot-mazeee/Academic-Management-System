package pt.ulisboa.tecnico.rnl.dei.dms.testgrade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.domain.TestGrade;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

@Repository
public interface TestGradeRepository extends JpaRepository<TestGrade, Long> {
    List<TestGrade> findAllByStudent(Person student);
    List<TestGrade> findAllByTest(Test test);
    TestGrade findByStudentAndTest(Person student, Test test);
}
