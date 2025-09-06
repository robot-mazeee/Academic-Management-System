package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.TestRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.repository.CurricularUnitRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Test;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.TestDto;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.file.domain.File;
import pt.ulisboa.tecnico.rnl.dei.dms.file.repository.FileRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.repository.TestGradeRepository;

@Transactional
@Service
public class TestService {
    @Autowired
	private TestRepository testRepository;

    @Autowired
	private CurricularUnitRepository curricularUnitRepository;

    @Autowired
	private TestGradeRepository testGradeRepository;

    @Autowired
	private FileRepository fileRepository;

    private CurricularUnit fetchCurricularUnitOrThrow(long curricularUnitId) {
        return curricularUnitRepository.findById(curricularUnitId)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_CURRICULAR_UNIT, Long.toString(curricularUnitId)));
    }

    private Test fetchTestOrThrow(long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_TEST, Long.toString(testId)));
    }

    private File fetchFileOrThrow(String fileName) {
        return fileRepository.findByName(fileName)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_FILE, fileName));
    }

    @Transactional
	public List<TestDto> getTests() {
		return testRepository.findAll().stream()
				.map(TestDto::new)
				.toList();
	}

    @Transactional
	public TestDto getTest(long testId) {
        Test test = fetchTestOrThrow(testId);
		return new TestDto(test);
	}

    @Transactional
	public TestDto createTest(TestDto testDto) {
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(testDto.curricularUnitId());

        Test test = new Test(testDto, curricularUnit);

		test.setId(null);
		return new TestDto(testRepository.save(test));
	}

    @Transactional
    public List<TestDto> getTestsByUc(long curricularUnitId) {
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(curricularUnitId);

        System.out.println(curricularUnit);

        return testRepository.findAllByCurricularUnit(curricularUnit).stream()
                .map(TestDto::new)
                .toList();
    }

    @Transactional
    public TestDto assignTestSheet(Long testId, String testSheet) {
        Test test = fetchTestOrThrow(testId);
        test.setTestSheet(testSheet);
        return new TestDto(test);
    }

    @Transactional
    public File getTestSheet(Long testId) {
        Test test = fetchTestOrThrow(testId);
        String TestSheetName = test.getTestSheet();
        return fetchFileOrThrow(TestSheetName);
    }

    @Transactional
	public TestDto updateTest(long testId, TestDto testDto) {
		Test existingTest = fetchTestOrThrow(testId);
        CurricularUnit curricularUnit = fetchCurricularUnitOrThrow(testDto.curricularUnitId());
		
		existingTest.setTitle(testDto.title());
		existingTest.setWeight(testDto.weight());
		existingTest.setCurricularUnit(curricularUnit);
        existingTest.setTestDate(testDto.testDate());

		return new TestDto(testRepository.save(existingTest));
	}

    @Transactional
	public void deleteTest(long testId) {
		fetchTestOrThrow(testId);
        testGradeRepository.deleteByTest_Id(testId);

		testRepository.deleteById(testId);
	}
}
