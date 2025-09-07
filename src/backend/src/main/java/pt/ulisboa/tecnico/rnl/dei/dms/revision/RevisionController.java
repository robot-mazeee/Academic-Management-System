package pt.ulisboa.tecnico.rnl.dei.dms.revision;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.dms.revision.dto.RevisionDto;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.dto.RevisionHistoryDto;
import pt.ulisboa.tecnico.rnl.dei.dms.revision.service.RevisionService;

@RestController
public class RevisionController {
	@Autowired
	private RevisionService revisionService;

    @GetMapping("/revisions") 
    public List<RevisionDto> getRevisions() {
        return revisionService.getRevisions();
    }

    @GetMapping("/revisions/student/{studentId}/test/{testId}")
    public RevisionDto getRevisionByStudentAndTest(@PathVariable long studentId, @PathVariable long testId) {
        return revisionService.getRevisionByStudentAndTest(studentId, testId);
    }

    @GetMapping("/revisions/student/{studentId}")
    public List<RevisionDto> getRevisionByStudent(@PathVariable long studentId) {
        return revisionService.getRevisionByStudent(studentId);
    }

    @GetMapping("/revisions/{revisionId}/history")
    public List<RevisionHistoryDto> getRevisionHistory(@PathVariable long revisionId) {
        return revisionService.getRevisionHistory(revisionId);
    }

    @PostMapping("/revisions") 
    public RevisionDto createRevision(@RequestBody RevisionDto revisionDto) {
        return revisionService.createRevision(revisionDto);
    }

    @PatchMapping("/revisions/{revisionId}")
    public RevisionDto updateStatus(@RequestBody RevisionDto revision) {
        return revisionService.updateStatus(revision);
    }
}