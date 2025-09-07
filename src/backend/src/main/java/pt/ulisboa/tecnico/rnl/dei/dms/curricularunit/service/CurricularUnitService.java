package pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.repository.CurricularUnitRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.EvaluationRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.service.EvaluationService;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person.PersonType;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.person.service.PersonService;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.dto.CurricularUnitDto;

// Service class for managing CurricularUnit entities
@Service
@Transactional
public class CurricularUnitService {
    @Autowired
	private CurricularUnitRepository curricularUnitRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private EvaluationRepository evaluationRepository;

	@Autowired
	private EvaluationService evaluationService;

	@Autowired
	private PersonService personService;

    private CurricularUnit fetchCurricularUnitOrThrow(long id) {
		return curricularUnitRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_CURRICULAR_UNIT, Long.toString(id)));
	}

	private Person fetchPersonOrThrow(long id) {
		return personRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PERSON, Long.toString(id)));
	}

	private void checkValidCurricularUnitDto(CurricularUnitDto curricularUnitDto) {
		if (curricularUnitDto.code() == null || curricularUnitDto.code().length() == 0) {
			throw new DEIException(ErrorMessage.CURRICULAR_UNIT_CODE_NOT_VALID);
		}
		if (curricularUnitDto.name() == null || curricularUnitDto.name().length() == 0) {
			throw new DEIException(ErrorMessage.CURRICULAR_UNIT_NAME_NOT_VALID);
		}
		if (curricularUnitDto.semester() == null) {
			throw new DEIException(ErrorMessage.CURRICULAR_UNIT_SEMESTER_NOT_VALID);
		}
		if (curricularUnitDto.course() == null) {
			throw new DEIException(ErrorMessage.CURRICULAR_UNIT_COURSE_NOT_VALID);
		}
		if (curricularUnitDto.mainTeacher() == null) {
			throw new DEIException(ErrorMessage.CURRICULAR_UNIT_MAIN_TEACHER_NOT_VALID);
		}
	}

    @Transactional
	public List<CurricularUnitDto> getCurricularUnits() {
		return curricularUnitRepository.findAll().stream()
				.map(CurricularUnitDto::new)
				.toList();
	}

    @Transactional
	public CurricularUnitDto createCurricularUnit(CurricularUnitDto curricularUnitDto) {
		checkValidCurricularUnitDto(curricularUnitDto);
		CurricularUnit curricularUnit = new CurricularUnit(curricularUnitDto);
		curricularUnit.setId(null);
		personService.updateType(curricularUnitDto.mainTeacher().getId(), PersonType.MAIN_TEACHER);
		return new CurricularUnitDto(curricularUnitRepository.save(curricularUnit));
	}

	@Transactional
	public CurricularUnitDto getCurricularUnit(long id) {
		return new CurricularUnitDto(fetchCurricularUnitOrThrow(id));
	}

	@Transactional
	public List<PersonDto> getCurricularUnitStudents(long id) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);
		return curricularUnit.getStudents().stream()
				.map(PersonDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public CurricularUnitDto assignCurricularUnitStudents(long id, List<PersonDto> studentDtos) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);

		List<Person> students = studentDtos.stream()
			.map(dto -> fetchPersonOrThrow(dto.id()))
			.collect(Collectors.toList());

		curricularUnit.setStudents(students);

		return new CurricularUnitDto(curricularUnitRepository.save(curricularUnit));
	}

	@Transactional
	public List<PersonDto> getCurricularUnitTeachingAssistants(long id) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);
		return curricularUnit.getTeachingAssistants().stream()
				.map(PersonDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public PersonDto getCurricularUnitMainTeacher(long id) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);
		return new PersonDto(curricularUnit.getMainTeacher());
	}

	@Transactional
	public List<PersonDto> getCurricularUnitPeople(long id) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);

		PersonDto mainTeacher = new PersonDto(curricularUnit.getMainTeacher());

		List<PersonDto> teachingAssistants = curricularUnit.getTeachingAssistants().stream()
				.map(PersonDto::new)
				.collect(Collectors.toList());

		List<PersonDto> students = curricularUnit.getStudents().stream()
				.map(PersonDto::new)
				.collect(Collectors.toList());

		List<PersonDto> allPeople = new ArrayList<>();
		allPeople.add(mainTeacher);
		allPeople.addAll(teachingAssistants);
		allPeople.addAll(students);

		return allPeople;
	}

	@Transactional
	public CurricularUnitDto assignCurricularUnitTeachingAssistants(long id, List<PersonDto> teacherDtos) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);

		Set<Person> teachers = teacherDtos.stream()
		.map(dto -> {
			personService.updateType(dto.id(), PersonType.TEACHING_ASSISTANT);
			return fetchPersonOrThrow(dto.id());
		})
		.collect(Collectors.toSet());


		curricularUnit.getTeachingAssistants().addAll(teachers);

		return new CurricularUnitDto(curricularUnitRepository.save(curricularUnit));
	}

	@Transactional
	public void removeTeachingAssistant(long curricularUnitId, long teacherId) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(curricularUnitId);
		Person teacher = fetchPersonOrThrow(teacherId);

		boolean removed = curricularUnit.getTeachingAssistants().remove(teacher);
		if (!removed) {
			throw new DEIException(ErrorMessage.NO_SUCH_TEACHING_ASSISTANT_FOR_UC);
		}
		
		personService.updateType(teacherId, PersonType.TEACHER);
		curricularUnitRepository.save(curricularUnit);
	}

	@Transactional
	public CurricularUnitDto updateCurricularUnit(long id, CurricularUnitDto curricularUnitDto) {
		checkValidCurricularUnitDto(curricularUnitDto);
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);
		personService.updateType(curricularUnit.getMainTeacher().getId(), PersonType.TEACHER);

		curricularUnit.setCode(curricularUnitDto.code());
		curricularUnit.setName(curricularUnitDto.name());
		curricularUnit.setSemester(curricularUnitDto.semester());
		curricularUnit.setCourse(curricularUnitDto.course());
		curricularUnit.setMainTeacher(curricularUnitDto.mainTeacher());
		personService.updateType(curricularUnit.getMainTeacher().getId(), PersonType.MAIN_TEACHER);

		return new CurricularUnitDto(curricularUnitRepository.save(curricularUnit));
	}

	@Transactional
	public List<PersonDto> addCurricularUnitStudent(long curricularUnitId, long studentId) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(curricularUnitId);
		Person student = fetchPersonOrThrow(studentId);

		if (!curricularUnit.getStudents().contains(student)) {
			curricularUnit.getStudents().add(student);
		}

		CurricularUnit saved = curricularUnitRepository.save(curricularUnit);

		return saved.getStudents().stream()
				.map(PersonDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public void removeCurricularUnitStudent(long curricularUnitId, long studentId) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(curricularUnitId);
		Person student = fetchPersonOrThrow(studentId);

		boolean removed = curricularUnit.getStudents().remove(student);
		if (!removed) {
			throw new DEIException(ErrorMessage.NO_SUCH_STUDENT_FOR_UC);
		}

		curricularUnitRepository.save(curricularUnit);
	}

	@Transactional
	public void deleteCurricularUnit(long id) {
		List<Evaluation> evaluations = evaluationRepository.findByCurricularUnit_Id(id);
        for (Evaluation e : evaluations) {
            evaluationService.deleteEvaluation(e.getId());
        }

		if (!enrollmentRepository.findByCurricularUnit_Id(id).isEmpty()) {
			throw new DEIException(ErrorMessage.CANNOT_DELETE_UC, Long.toString(id));
		}
		
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);
		personService.updateType(curricularUnit.getMainTeacher().getId(), PersonType.TEACHER);

		curricularUnitRepository.deleteById(id);
	}
}
