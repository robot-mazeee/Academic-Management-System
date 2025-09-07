package pt.ulisboa.tecnico.rnl.dei.dms.testgrade.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.domain.TestGrade;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

@Repository
public interface TestGradeRepository extends JpaRepository<TestGrade, Long> {
    List<TestGrade> findAllByStudent(Person student);
    List<TestGrade> findAllByTest(Test test);
    Optional<TestGrade> findByStudentAndTest(Person student, Test test);
    void deleteByTest_Id(long testId);

    @Modifying
    @Query("DELETE FROM TestGrade tg WHERE tg.student.id = :studentId")
    void deleteByStudentId(@Param("studentId") Long studentId);
}
