package pt.ulisboa.tecnico.rnl.dei.dms.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.service.PersonService;

@RestController
public class PersonController {
	@Autowired
	private PersonService personService;

	@GetMapping("/people")
	public List<PersonDto> getPeople() {
		return personService.getPeople();
	}

	@GetMapping("/teachers")
	public List<PersonDto> getTeachers() {
		return personService.getTeachers();
	}

	@GetMapping("/teaching-assistants")
	public List<PersonDto> getTeachingAssistants() {
		return personService.getTeachingAssistants();
	}

	@GetMapping("/students")
	public List<PersonDto> getStudents() {
		return personService.getStudents();
	}

	@PostMapping("/people")
    public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.createPerson(personDto));
    }

    @PutMapping("people/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.updatePerson(id, personDto));
    }

	@GetMapping("/people/{id}")
	public PersonDto getPerson(@PathVariable long id) {
		return personService.getPerson(id);
	}

	@DeleteMapping("/people/{id}")
	public void deletePerson(@PathVariable long id) {
		personService.deletePerson(id);
	}
}