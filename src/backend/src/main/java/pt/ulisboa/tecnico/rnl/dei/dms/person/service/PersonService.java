package pt.ulisboa.tecnico.rnl.dei.dms.person.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person.PersonType;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;

// Service class for managing Person entities
@Service
@Transactional
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	private Person fetchPersonOrThrow(long id) {
		return personRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PERSON, Long.toString(id)));
	}

	private List<PersonDto> getPeopleByType(List<PersonType> types) {
		return personRepository.findAllByTypeIn(types).stream()
				.map(PersonDto::new)
				.toList();
	}

	@Transactional
	public List<PersonDto> getPeople() {
		return personRepository.findAll().stream()
				.map(PersonDto::new)
				.toList();
	}

	@Transactional
	public List<PersonDto> getTeachers() {
		return getPeopleByType(List.of(PersonType.TEACHER));
	}

	@Transactional
	public List<PersonDto> getTeachingAssistants() {
		return getPeopleByType(List.of(PersonType.TEACHING_ASSISTANT));
	}

	@Transactional
	public List<PersonDto> getStudents() {
		return getPeopleByType(List.of(PersonType.STUDENT));
	}

	@Transactional
	public PersonDto createPerson(PersonDto personDto) {
		Person person = new Person(personDto);
		person.setId(null);
		return new PersonDto(personRepository.save(person));
	}

	@Transactional
	public PersonDto getPerson(long id) {
		return new PersonDto(fetchPersonOrThrow(id));
	}

	@Transactional
	public PersonDto updatePerson(long id, PersonDto personDto) {
		Person existingPerson = fetchPersonOrThrow(id);
		
		existingPerson.setName(personDto.name());
		existingPerson.setEmail(personDto.email());
		existingPerson.setIstId(personDto.istId());
		existingPerson.setType(PersonType.valueOf(personDto.type().toUpperCase()));

		return new PersonDto(personRepository.save(existingPerson));
	}

	@Transactional
	public PersonDto updateType(long id, PersonType newType) {
		Person person = fetchPersonOrThrow(id);
		person.setType(newType);
		return new PersonDto(personRepository.save(person));
	}

	@Transactional
	public void deletePerson(long id) {
		fetchPersonOrThrow(id); // ensure exists

		personRepository.deleteById(id);
	}
}
