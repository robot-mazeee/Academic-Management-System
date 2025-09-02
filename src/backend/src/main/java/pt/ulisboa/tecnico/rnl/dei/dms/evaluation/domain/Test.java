package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;

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
}
