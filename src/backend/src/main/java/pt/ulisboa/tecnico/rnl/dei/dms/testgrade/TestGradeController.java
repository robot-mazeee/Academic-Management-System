package pt.ulisboa.tecnico.rnl.dei.dms.testgrade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.dto.TestGradeDto;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.service.TestGradeService;

@RestController
public class TestGradeController {
	@Autowired
	private TestGradeService testGradeService;

    @GetMapping("/student/{studentId}/test/{testId}")
	public TestGradeDto getTestGradeByTestAndStudent(@PathVariable long studentId, @PathVariable long testId) {
	    return testGradeService.getTestGradesByStudentByTest(studentId, testId);
	}

    @GetMapping("/student/{studentId}/test-grades")
	public List<TestGradeDto> getTestGradesByStudent(@PathVariable long studentId) {
	    return testGradeService.getTestGradesByStudent(studentId);
	}

    @PostMapping("/test-grades")
	public TestGradeDto createTestGrade(@RequestBody TestGradeDto testGradeDto) {
		return testGradeService.createTestGrade(testGradeDto);
	}
}