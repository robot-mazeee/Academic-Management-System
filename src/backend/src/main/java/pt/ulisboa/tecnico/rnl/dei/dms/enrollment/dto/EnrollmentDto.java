package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

public record EnrollmentDto(long id, Person student, long curricularUnitId, String status, double finalGrade) {
	public EnrollmentDto(Enrollment enrollment) {
		this(
			enrollment.getId(), 
			enrollment.getStudent(), 
			enrollment.getCurricularUnit().getId(),
			enrollment.getStatus().toString(),
			enrollment.getFinalGrade()
		);
	}
}
