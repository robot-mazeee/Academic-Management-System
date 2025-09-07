package pt.ulisboa.tecnico.rnl.dei.dms.course.dto;

import jakarta.validation.constraints.*;
import pt.ulisboa.tecnico.rnl.dei.dms.course.domain.Course;

public record CourseDto(long id, 
	@NotBlank(message = "Campo nome é obrigatório")
    @Size(min = 3, max = 50, message = "Nome tem de ser 3-50 charateres")
	String name, 

	@NotBlank(message = "Campo código é obrigatório")
    @Size(min = 3, max = 20, message = "Código tem de ser 3-20 charateres")
	String code, 
	
	@NotBlank(message = "Campo duração é obrigatório")
	int duration) {
	public CourseDto(Course course) {
		this(
			course.getId(), 
			course.getName(), 
			course.getCode(),
            course.getDuration()
		);
	}
}
