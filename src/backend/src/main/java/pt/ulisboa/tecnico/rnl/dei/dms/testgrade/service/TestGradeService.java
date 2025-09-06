package pt.ulisboa.tecnico.rnl.dei.dms.testgrade.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.dto.TestGradeDto;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.domain.TestGrade;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.repository.TestGradeRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.TestRepository;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.file.domain.File;
import pt.ulisboa.tecnico.rnl.dei.dms.file.repository.FileRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;

@Service
@Transactional
public class TestGradeService {
	@Autowired
	private TestGradeRepository testGradeRepository;

    @Autowired
	private PersonRepository personRepository;

    @Autowired
	private TestRepository testRepository;

	@Autowired
	private FileRepository fileRepository;

	private File fetchFileOrThrow(String fileName) {
        return fileRepository.findByName(fileName)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_FILE, fileName));
    }

	private TestGrade fetchTestGradeOrThrow(long id) {
		return testGradeRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_TEST, Long.toString(id)));
	}

    private Person fetchPersonOrThrow(long id) {
		return personRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_PERSON, Long.toString(id)));
	}

    private Test fetchTestOrThrow(long id) {
		return testRepository.findById(id)
				.orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_TEST_GRADE, Long.toString(id)));
	}

    @Transactional
	public List<TestGradeDto> getTestGrades() {
		return testGradeRepository.findAll().stream()
				.map(TestGradeDto::new)
				.toList();
	}

    @Transactional
	public List<TestGradeDto> getTestGradesByStudent(long studentId) {
        Person student = fetchPersonOrThrow(studentId);

		return testGradeRepository.findAllByStudent(student).stream()
				.map(TestGradeDto::new)
				.toList();
	}

    @Transactional
	public TestGradeDto getTestGradesByStudentByTest(long studentId, long testId) {
        Person student = fetchPersonOrThrow(studentId);
        Test test = fetchTestOrThrow(testId);

		return new TestGradeDto(testGradeRepository.findByStudentAndTest(student, test));
	}

	@Transactional
	public List<TestGradeDto> getTestGradesByTest(long testId) {
        Test test = fetchTestOrThrow(testId);

		return testGradeRepository.findAllByTest(test).stream()
				.map(TestGradeDto::new)
				.toList();
	}

    @Transactional
	public TestGradeDto createTestGrade(TestGradeDto testGradeDto) {
		TestGrade testGrade = new TestGrade(testGradeDto);
		testGrade.setId(null);
		return new TestGradeDto(testGradeRepository.save(testGrade));
	}

	@Transactional
    public TestGradeDto assignCorrection(Long testGradeId, String correction) {
        TestGrade grade = fetchTestGradeOrThrow(testGradeId);
        grade.setCorrection(correction);
        return new TestGradeDto(grade);
    }

    @Transactional
    public File getCorrection(Long testGradeId) {
        TestGrade testGrade = fetchTestGradeOrThrow(testGradeId);
        String correctionName = testGrade.getCorrection();
        return fetchFileOrThrow(correctionName);
    }
}