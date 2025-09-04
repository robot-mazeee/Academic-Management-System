package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.service.TestService;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.TestDto;

@RestController
class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/tests")
    public List<TestDto> getTests() {
        return testService.getTests();
    }

    @GetMapping("/tests/{id}")
    public TestDto getTest(@PathVariable long id) {
        return testService.getTest(id);
    }

    @GetMapping("/curricular-units/{ucId}/tests")
    public List<TestDto> getTestsByUC(@PathVariable Long ucId) {
        return testService.getTestsByUc(ucId);
    }

    @PostMapping("curricular-units/{ucId}/tests")
    public TestDto createTest(@RequestBody TestDto testDto) {
        return testService.createTest(testDto);
    }

    @PutMapping("tests/{testId}")
    public void updateTest(@PathVariable long testId, @RequestBody TestDto testDto) {
        testService.updateTest(testId, testDto);
    }

    @DeleteMapping("tests/{testId}")
    public void deleteTest(@PathVariable long testId) {
        testService.deleteTest(testId);
    }
}