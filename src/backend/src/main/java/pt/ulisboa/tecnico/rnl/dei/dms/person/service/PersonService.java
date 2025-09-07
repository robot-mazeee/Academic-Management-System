package pt.ulisboa.tecnico.rnl.dei.dms.person.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.repository.CurricularUnitRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person.PersonType;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository.GroupRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.projectsubmission.repository.ProjectSubmissionRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.repository.RevisionRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.repository.TestGradeRepository;

// Service class for managing Person entities
@Service
@Transactional
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

		@Autowired
	private CurricularUnitRepository curricularUnitRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private RevisionRepository revisionRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private ProjectSubmissionRepository projectSubmissionRepository;


	private TestGradeRepository testGradeRepository;

	private Person fetchPersonOrThrow(long id) {
		return personRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PERSON, Long.toString(id)));
	}

	private List<PersonDto> getPeopleByType(List<PersonType> types) {
		return personRepository.findAllByTypeIn(types).stream()
				.map(PersonDto::new)
				.toList();
	}

	private void checkValidPersonDto(PersonDto personDto) {
		if (personDto.name() == null || personDto.name().length() == 0) {
			throw new DEIException(ErrorMessage.PERSON_NAME_NOT_VALID);
		}
		if (personDto.istId() == null || personDto.istId().length() == 0) {
			throw new DEIException(ErrorMessage.PERSON_ISTID_NOT_VALID);
		}
		if (personDto.email() == null || !personDto.email().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
			throw new DEIException(ErrorMessage.PERSON_EMAIL_NOT_VALID);
		}
		if (personDto.type() == null) {
			throw new DEIException(ErrorMessage.PERSON_TYPE_NOT_VALID);
		}
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
		checkValidPersonDto(personDto);
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
		checkValidPersonDto(personDto);
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
	public void deletePerson(long personId) {
		Person person = fetchPersonOrThrow(personId);

		// remove curricular unit references
		curricularUnitRepository.findAll().forEach(cu -> {
			cu.getStudents().remove(person);
			cu.getTeachingAssistants().remove(person);
			if (person.equals(cu.getMainTeacher())) cu.setMainTeacher(null);
		});

		// delete test grades
		testGradeRepository.deleteByStudentId(personId);

		// delete all enrollments
		enrollmentRepository.deleteByStudentId(personId);

		// delete all group references
		groupRepository.findAll().forEach(group -> {
			group.getMembers().remove(person);
			if (group.getMembers().isEmpty()) {
				projectSubmissionRepository.deleteByGroup_Id(group.getId());
				groupRepository.delete(group);
			}
		});

		// delete all revisions
		revisionRepository.deleteByStudent_Id(personId);

		// delete the person
		personRepository.deleteById(personId);
	}
}
