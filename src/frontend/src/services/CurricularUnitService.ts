import type PersonDto from "@/models/PersonDto";
import httpClient from "./ApiClient";
import type CurricularUnitDto from "@/models/CurricularUnitDto";

export default {
  getCurricularUnits(): Promise<CurricularUnitDto[]> {
    return httpClient.get("/curricular-units");
  },

  getCurricularUnitStudents(id: number): Promise<PersonDto[]> {
    return httpClient.get(`/curricular-units/${id}/students`);
  },

  createCurricularUnit(curricularUnit: CurricularUnitDto): Promise<CurricularUnitDto> {
    return httpClient.post("/curricular-units", curricularUnit);
  },

  deleteCurricularUnit(curricularUnit: CurricularUnitDto) {
    return httpClient.delete(`/curricular-units/${curricularUnit.id}`);
  },

  editCurricularUnit(curricularUnit: CurricularUnitDto) {
    return httpClient.put(`/curricular-units/${curricularUnit.id}`, curricularUnit);
  },

  assignCurricularUnitStudents(id: number, students: PersonDto[]) {
    return httpClient.patch(`/curricular-units/${id}/students`, students);
  },
};