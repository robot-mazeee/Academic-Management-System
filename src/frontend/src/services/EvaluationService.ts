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

  getTest(testId: number): Promise<TestDto> {
    return httpClient.get(`/tests/${testId}`);
  },

  getTestGrades(curricularUnitId: number, testId: number): Promise<TestGradeDto[]> {
    return httpClient.get(`curricular-units/${curricularUnitId}/tests/${testId}/grades`);
  },

  createTestGrade(testGrade: TestGradeDto) {
    return httpClient.post("test-grades", testGrade)
  },

  getStudentTestGrades(studentId: number): Promise<TestGradeDto[]> {
    return httpClient.get(`/students/${studentId}/test-grades`)
  }
}
