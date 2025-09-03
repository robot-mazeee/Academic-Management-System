package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

@Getter
@Setter
@Entity
public class Enrollment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Person student;

    @ManyToOne
    private CurricularUnit curricularUnit;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    private double finalGrade;

    protected Enrollment() {}

    public Enrollment(Person student, CurricularUnit curricularUnit, EnrollmentStatus status, double finalGrade) {
        this.student = student;
        this.curricularUnit = curricularUnit;
        this.status = status;
        this.finalGrade = finalGrade;
    }

    public Enrollment(EnrollmentDto enrollmentDto, CurricularUnit curricularUnit) {
        this(
            enrollmentDto.student(),
			curricularUnit,
            EnrollmentStatus.valueOf(enrollmentDto.status().toUpperCase()),
            enrollmentDto.finalGrade()
        );
        System.out.println("Enrollment Dto: " + enrollmentDto);
    }
}
