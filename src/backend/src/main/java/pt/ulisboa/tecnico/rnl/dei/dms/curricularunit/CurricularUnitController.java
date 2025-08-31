package pt.ulisboa.tecnico.rnl.dei.dms.curricularunit;

import java.util.List;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.service.CurricularUnitService;
import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.dto.CurricularUnitDto;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class CurricularUnitController {
	@Autowired
	private CurricularUnitService curricularUnitService;

	@GetMapping("/curricular-units")
	public List<CurricularUnitDto> getCurricularUnits() {
		return curricularUnitService.getCurricularUnits();
	}

	@PostMapping("/curricular-units")
	public CurricularUnitDto createCurricularUnit(@RequestBody CurricularUnitDto CurricularUnitDto) {
		return curricularUnitService.createCurricularUnit(CurricularUnitDto);
	}

	@GetMapping("/curricular-units/{id}")
	public CurricularUnitDto getCurricularUnit(@PathVariable long id) {
		return curricularUnitService.getCurricularUnit(id);
	}

	@PutMapping("/curricular-units/{id}")
	public CurricularUnitDto updateCurricularUnit(@PathVariable long id, @RequestBody CurricularUnitDto CurricularUnitDto) {
		return curricularUnitService.updateCurricularUnit(id, CurricularUnitDto);
	}

	@DeleteMapping("/curricular-units/{id}")
	public void deleteCurricularUnit(@PathVariable long id) {
		curricularUnitService.deleteCurricularUnit(id);
	}
}