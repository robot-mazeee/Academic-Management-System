package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {

	NO_SUCH_PERSON("Não existe nenhuma pessoa com o ID %s", 1001),
	PERSON_NAME_NOT_VALID("O nome da pessoa especificado não é válido.", 1002),
	PERSON_ALREADY_EXISTS("Já existe uma pessoa com o ID %s", 1003),
	NO_SUCH_CURRICULAR_UNIT("Não existe nenhuma unidade curricular com o ID %s", 1004),
	NO_SUCH_TEST("Não existe nenhum teste com o ID %s", 1005),
	NO_SUCH_ENROLLMENT("Não existe nenhuma incrição com o ID %s", 1006),
	NO_SUCH_TEACHING_ASSISTANT_FOR_UC("Não existe na UC nenhum professor assistente com ID %s", 1007),
	NO_SUCH_STUDENT_FOR_UC("Não existe na UC nenhum aluno com ID %s", 1008),
	NO_SUCH_COURSE("Não existe nenhum curso com ID %s", 1009),
	NO_SUCH_PROJECT("Não existe nenhum projeto com ID %s", 1010);

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
