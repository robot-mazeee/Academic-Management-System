package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {
	NO_SUCH_PERSON("Não existe nenhuma pessoa com o ID %s", 1001),
	PERSON_ALREADY_EXISTS("Já existe uma pessoa com o ID %s", 1003),
	PERSON_NAME_NOT_VALID("O nome da pessoa especificado não é válido.", 1002),
	PERSON_ISTID_NOT_VALID("O IstId da pessoa especificado não é válido.", 1002),
	PERSON_EMAIL_NOT_VALID("O email da pessoa especificado não é válido.", 1002),
	PERSON_TYPE_NOT_VALID("O tipo da pessoa especificado não é válido.", 1002),

	NO_SUCH_COURSE("Não existe nenhum curso com ID %s", 1009),
	COURSE_CODE_NOT_VALID("O código do curso especificado não é válido.", 1009),
	COURSE_NAME_NOT_VALID("O nome do curso especificado não é válido.", 1009),
	COURSE_DURATION_NOT_VALID("A duração do curso especificado não é válido.", 1009),

	NO_SUCH_TEST("Não existe nenhum teste com o ID %s", 1005),
	TEST_DATE_NOT_VALID("A data do teste especificada não é válida.", 1005),
	TEST_TITLE_NOT_VALID("O título do teste especificado não é válido.", 1005),

	REVISION_REASON_NOT_VALID("A justificação de revisão especificada não é válida.", 1005),

	NO_SUCH_CURRICULAR_UNIT("Não existe nenhuma unidade curricular com o ID %s", 1004),
	NO_SUCH_TEACHING_ASSISTANT_FOR_UC("Não existe na UC nenhum professor assistente com ID %s", 1007),
	NO_SUCH_STUDENT_FOR_UC("Não existe na UC nenhum aluno com ID %s", 1008),
	CURRICULAR_UNIT_MAIN_TEACHER_NOT_VALID("O professor regente especificado não é válido.", 1005),
	CURRICULAR_UNIT_CODE_NOT_VALID("O código especificado não é válido.", 1005),
	CURRICULAR_UNIT_NAME_NOT_VALID("O nome especificado não é válido.", 1005),
	CURRICULAR_UNIT_SEMESTER_NOT_VALID("O semestre especificado não é válido.", 1005),
	CURRICULAR_UNIT_COURSE_NOT_VALID("O curso especificado não é válido.", 1005),

	NO_SUCH_ENROLLMENT("Não existe nenhuma incrição com o ID %s", 1006),
	
	NO_SUCH_PROJECT("Não existe nenhum projeto com ID %s", 1010),
	CANNOT_DELETE_UC("Não é permitido apagar a UC %s (ID) com inscrições ativas", 1011),
	NO_SUCH_PROJECT_SUBMISSION("Não existe nenhuma submissão de projeto com ID %s", 1012),
	NO_SUCH_TEST_GRADE("Não existe nenhuma nota de teste com ID %s", 1013),
	NO_SUCH_FILE("Não existe nenhum ficheiro com nome %s", 1014),
	FILE_TOO_LARGE("Ficheiro demasiado grande", 1015),
	NO_SUCH_REVISION("Não existe nenhum workflow de revisão com ID %s", 1016);

	private final String label;
	private final int code;

	ErrorMessage(String label, int code) {
		this.label = label;
		this.code = code;
	}

	public String getLabel() {
		return this.label;
	}

	public int getCode() {
		return this.code;
	}
}
