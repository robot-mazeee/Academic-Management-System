package pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.curricularunit.domain.CurricularUnit;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;

// Data Transfer Object, to communicate with frontend
public record CurricularUnitDto(long id, String code, String name, Integer semester, String course, PersonDto mainTeacher) {
	public CurricularUnitDto(CurricularUnit curricularUnit) {
		this(
			curricularUnit.getId(), 
			curricularUnit.getCode(), 
			curricularUnit.getName(),
			curricularUnit.getSemester(),
			curricularUnit.getCourse(),
			curricularUnit.getMainTeacher()
		);
	}
}