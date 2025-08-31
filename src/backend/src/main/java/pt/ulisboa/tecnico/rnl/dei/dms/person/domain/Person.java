package pt.ulisboa.tecnico.rnl.dei.dms.person.domain;


import jakarta.persistence.*;

import lombok.Data;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;

// Domain class representing a person in the system
@Data
@Entity
@Table(name = "people")
public class Person {

	public enum PersonType {
		ADMINISTRATOR,
		MAIN_TEACHER,
		TEACHING_ASSISTANT,
		STUDENT
		// maybe add more types later?
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "ist_id", nullable = false, unique = true)
	private String istId;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
    private PersonType type;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	protected Person() {
	}

	public Person(String name, String istId, PersonType type, String email) {
		this.name = name;
		this.istId = istId;
		this.type = type;
		this.email = email;
	}

	public Person(PersonDto personDto) {
		this(
			personDto.name(), 
			personDto.istId(),
			PersonType.valueOf(personDto.type().toUpperCase()),
			personDto.email()
		);
		System.out.println("PersonDto: " + personDto);
		System.out.println("PersonType: " + personDto.type());
	}
}
