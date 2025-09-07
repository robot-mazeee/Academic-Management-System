package pt.ulisboa.tecnico.rnl.dei.dms.person.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

import jakarta.validation.constraints.*;

public record PersonDto(
	long id,
    @NotBlank(message = "Campo nome é obrigatório")
    @Size(min = 3, max = 50, message = "Nome tem de ser 3-20 charateres")
    String name,

    @NotBlank(message = "Campo IstId é obrigatório")
    @Pattern(regexp = "^ist\\d+$", message = "Campo IstId tem de começar com 'ist' seguido de números")
    String istId,

    @NotNull(message = "Campo tipo é obrigatório")
    String type,

    @NotBlank(message = "Campo Email é obrigatório")
    @Email(message = "Formato de email inválido")
    String email
) {
    public PersonDto(Person person) {
        this(
			person.getId(),
            person.getName(),
            person.getIstId(),
            person.getType() != null ? person.getType().toString() : null,
            person.getEmail()
        );
    }
}
