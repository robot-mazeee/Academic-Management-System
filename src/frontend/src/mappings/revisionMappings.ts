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
