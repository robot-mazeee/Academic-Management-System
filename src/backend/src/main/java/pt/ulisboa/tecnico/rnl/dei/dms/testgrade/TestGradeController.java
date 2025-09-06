package pt.ulisboa.tecnico.rnl.dei.dms.testgrade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.dms.file.domain.File;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.dto.TestGradeDto;
import pt.ulisboa.tecnico.rnl.dei.dms.testgrade.service.TestGradeService;

@RestController
public class TestGradeController {
	@Autowired
	private TestGradeService testGradeService;

    @GetMapping("curricular-units/{curricularUnitId}/tests/{testId}/grades/students/{studentId}")
	public TestGradeDto getTestGradeByTestAndStudent(@PathVariable long studentId, @PathVariable long testId) {
	    return testGradeService.getTestGradesByStudentByTest(studentId, testId);
	}

    @GetMapping("/students/{studentId}/test-grades")
	public List<TestGradeDto> getTestGradesByStudent(@PathVariable long studentId) {
	    return testGradeService.getTestGradesByStudent(studentId);
	}

	@GetMapping("curricular-units/{curricularUnitId}/tests/{testId}/grades")
	public List<TestGradeDto> getTestGradesByTest(@PathVariable long testId) {
	    return testGradeService.getTestGradesByTest(testId);
	}

    @PostMapping("/test-grades")
	public TestGradeDto createTestGrade(@RequestBody TestGradeDto testGradeDto) {
		return testGradeService.createTestGrade(testGradeDto);
	}

	@PatchMapping("/test-grades/{testGradeId}/correction")
    public TestGradeDto assignCorrection(@PathVariable Long testGradeId, @RequestBody String correction) {
        return testGradeService.assignCorrection(testGradeId, correction);
    }

    @GetMapping("/test-grades/{testGradeId}/correction")
    public File getCorrection(@PathVariable long testGradeId) {
        return testGradeService.getCorrection(testGradeId);
    }
}