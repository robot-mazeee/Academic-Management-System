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

@Transactional
@Service
public class TestService {
    @Autowired
	private TestRepository testRepository;

    @Autowired
	private CurricularUnitRepository curricularUnitRepository;

    @Transactional
	public List<TestDto> getTests() {
		return testRepository.findAll().stream()
				.map(TestDto::new)
				.toList();
	}

    @Transactional
	public TestDto createTest(TestDto testDto) {
        CurricularUnit curricularUnit = curricularUnitRepository.findById(testDto.curricularUnitId())
            .orElseThrow(() -> new RuntimeException("UC não encontrada"));

        Test test = new Test(testDto, curricularUnit);

		test.setId(null);
		return new TestDto(testRepository.save(test));
	}

    @Transactional
    public List<TestDto> getTestsByUc(long curricularUnitId) {
        return testRepository.findAllByCurricularUnit(curricularUnitId).stream()
				.map(TestDto::new)
				.toList();
    }
}
