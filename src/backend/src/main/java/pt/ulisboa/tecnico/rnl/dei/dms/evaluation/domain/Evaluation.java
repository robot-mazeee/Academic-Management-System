package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;

// Domain class representing an evaluation in the system
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Evaluation {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "weight")
	private Double weight;

	@ManyToOne
    private CurricularUnit curricularUnit;

	protected Evaluation() {}

	public Evaluation(String title, Double weight, CurricularUnit curricularUnit) {
		this.title = title;
		this.weight = weight;
		this.curricularUnit = curricularUnit;
	}
}

