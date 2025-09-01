package pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.repository.CurricularUnitRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;
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

    private CurricularUnit fetchCurricularUnitOrThrow(long id) {
		return curricularUnitRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_CURRICULAR_UNIT, Long.toString(id)));
	}

    @Transactional
	public List<CurricularUnitDto> getCurricularUnits() {
		return curricularUnitRepository.findAll().stream()
				.map(CurricularUnitDto::new)
				.toList();
	}

    @Transactional
	public CurricularUnitDto createCurricularUnit(CurricularUnitDto curricularUnitDto) {
		CurricularUnit curricularUnit = new CurricularUnit(curricularUnitDto);
		curricularUnit.setId(null);
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
			.map(dto -> personRepository.findById(dto.id())
					.orElseThrow(() -> new RuntimeException("Student not found: " + dto.id())))
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
	public CurricularUnitDto assignCurricularUnitTeachingAssistants(long id, List<PersonDto> teacherDtos) {
		CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(id);

		List<Person> teachers = teacherDtos.stream()
			.map(dto -> personRepository.findById(dto.id())
					.orElseThrow(() -> new RuntimeException("Teaching assistant not found: " + dto.id())))
			.collect(Collectors.toList());

		curricularUnit.setTeachingAssistants(teachers);

		return new CurricularUnitDto(curricularUnitRepository.save(curricularUnit));
	}

	@Transactional
	public CurricularUnitDto updateCurricularUnit(long id, CurricularUnitDto curricularUnitDto) {
		fetchCurricularUnitOrThrow(id);
		CurricularUnit curricularUnit = new CurricularUnit(curricularUnitDto);
		curricularUnit.setId(id);
		return new CurricularUnitDto(curricularUnitRepository.save(curricularUnit));
	}

	@Transactional
	public void deleteCurricularUnit(long id) {
		fetchCurricularUnitOrThrow(id);
		curricularUnitRepository.deleteById(id);
	}
}
