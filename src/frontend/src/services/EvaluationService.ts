import httpClient from "./ApiClient";
import type TestDto from "@/models/TestDto";

export default {
  getTests(): Promise<TestDto[]> {
    return httpClient.get("/tests");
  },

  createTest(testDto: TestDto): Promise<TestDto> {
    return httpClient.post(`/curricular-unit/${testDto.curricularUnitId}/tests`, testDto);
  }
}
