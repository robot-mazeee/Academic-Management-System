export const getColorByType = (type: string) => {
  switch (type) {
    case "ADMINISTRATOR":
      return "purple";
    case "MAIN_TEACHER":
      return "red";
    case "TEACHING_ASSISTANT":
      return "blue";
    case "STUDENT":
      return "green";
    case "TEACHER":
      return "yellow";
    default:
      return "grey"
  }
};


export const translateType = (type: string) => {
  switch (type) {
    case "ADMINISTRATOR":
      return "Administrador";
    case "MAIN_TEACHER":
      return "Professor Regente";
    case "TEACHING_ASSISTANT":
      return "Professor Assistente";
    case "STUDENT":
      return "Aluno";
    case "TEACHER":
      return "Professor";
    default:
      return "Default";
  }
};

export const typeMappings = {
  'Administrador': 'ADMINISTRATOR',
  'Professor Regente': 'MAIN_TEACHER',
  'Professor Assistente': 'TEACHING_ASSISTANT',
  'Aluno': 'STUDENT',
  'Professor': 'TEACHER'
}