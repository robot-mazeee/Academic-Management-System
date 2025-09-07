export const getColorByStatus = (status: string) => {
  switch (status) {
    case "REQUESTED_BY_STUDENT":
      return "purple";
    case "REJECTED":
      return "red";
    case "APPROVED_BY_TEACHING_ASSISTANT":
      return "blue";
    case "APPROVED":
      return "green";
    default:
      return "grey"
  }
};

export const translateStatus = (status: string) => {
  switch (status) {
    case "REQUESTED_BY_STUDENT":
      return "Pedido pelo Aluno";
    case "REJECTED":
      return "Rejeitado";
    case "APPROVED_BY_TEACHING_ASSISTANT":
      return "Aprovado pelo Professor Assistente";
    case "APPROVED":
      return "Aprovado";
    default:
      return "Default";
  }
};
