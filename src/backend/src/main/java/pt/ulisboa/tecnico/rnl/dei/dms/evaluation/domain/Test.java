package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.TestDto;

@Getter
@Setter
@Entity
public class Test extends Evaluation {
    @Column(name = "test_date")
    private LocalDate testDate;

    // private List<TestGrades> testGrades;

    protected Test() {}

    public Test(String title, Double weight, CurricularUnit curricularUnit, LocalDate testDate) {
        super(title, weight, curricularUnit);
        this.testDate = testDate;
    }

    public Test(TestDto testDto, CurricularUnit curricularUnit) {
		this(
			testDto.title(), 
			testDto.weight(),
			curricularUnit,
            testDto.testDate()
		);
		System.out.println("Test Dto: " + testDto);
	}
}
