import type TestGradeDto from "@/models/TestGradeDto";
import httpClient from "./ApiClient";
import type TestDto from "@/models/TestDto";

export default {
  getTests(): Promise<TestDto[]> {
    return httpClient.get("/tests");
  },

  createTest(testDto: TestDto): Promise<TestDto> {
    return httpClient.post(`/curricular-units/${testDto.curricularUnitId}/tests`, testDto);
  },

  getTestGrades(curricularUnitId: number, testId: number): Promise<TestGradeDto[]> {
    return httpClient.get(`curricular-units/${curricularUnitId}/tests/${testId}/grades`);
  },

  createTestGrade(testGrade: TestGradeDto) {
    return httpClient.post("test-grades", testGrade)
  },
}
