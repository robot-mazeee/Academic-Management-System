package pt.ulisboa.tecnico.rnl.dei.dms.person.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

public record PersonDto(long id, String name, String istId, String type, String email) {
    public PersonDto(Person person) {
        this(
			person.getId(),
            person.getName(),
            person.getIstId(),
            person.getType().toString(),
            person.getEmail()
        );
    }
}
