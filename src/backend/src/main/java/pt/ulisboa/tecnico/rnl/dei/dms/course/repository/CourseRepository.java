package pt.ulisboa.tecnico.rnl.dei.dms.course.repository;

import pt.ulisboa.tecnico.rnl.dei.dms.course.domain.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {

}
