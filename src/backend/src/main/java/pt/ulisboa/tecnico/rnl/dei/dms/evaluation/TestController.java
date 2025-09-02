package pt.ulisboa.tecnico.rnl.dei.dms.evaluation;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/curricular-unit/{ucId}/tests")
    public List<TestDto> getTestsByUC(@PathVariable Long ucId) {
        return testService.getTestsByUc(ucId);
    }

    @PostMapping("curricular-unit/{ucId}/tests")
    public TestDto createTest(@RequestBody TestDto testDto) {
        return testService.createTest(testDto);
    }
}