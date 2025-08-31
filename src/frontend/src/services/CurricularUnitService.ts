import httpClient from "./ApiClient";
import type CurricularUnitDto from "@/models/CurricularUnitDto";

export default {
  getCurricularUnits(): Promise<CurricularUnitDto[]> {
    return httpClient.get("/curricular-units");
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
};