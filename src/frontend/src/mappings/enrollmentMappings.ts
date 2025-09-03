export const getColorByStatus = (status: string) => {
  switch (status) {
    case "FAILED":
      return "red";
    case "APPROVED":
      return "green";
    case "ENROLLED":
      return "blue";
    default:
      return "yellow"
  }
};


export const translateStatus = (status: string) => {
  switch (status) {
    case "FAILED":
      return "Reprovado";
    case "APPROVED":
      return "Aprovado";
    case "ENROLLED":
      return "Inscrito";
    default:
      return "Sem inscrição.";
  }
};