import type TestGradeDto from "@/models/TestGradeDto";
import httpClient from "./ApiClient";
import type TestDto from "@/models/TestDto";
import type ProjectDto from "@/models/ProjectDto";
import type ProjectSubmissionDto from "@/models/ProjectSubmissionDto";
import type GroupDto from "@/models/GroupDto";

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

  updateTest(testDto: TestDto): Promise<TestDto> {
    return httpClient.put(`/tests/${testDto.id}`, testDto)
  },

  deleteTest(testId: number) {
    return httpClient.delete(`/tests/${testId}`)
  },

  getTestGrades(curricularUnitId: number, testId: number): Promise<TestGradeDto[]> {
    return httpClient.get(`/curricular-units/${curricularUnitId}/tests/${testId}/grades`);
  },

  createTestGrade(testGrade: TestGradeDto) {
    return httpClient.post("/test-grades", testGrade)
  },

  getStudentTestGrades(studentId: number): Promise<TestGradeDto[]> {
    return httpClient.get(`/students/${studentId}/test-grades`)
  },

  getProjects(): Promise<ProjectDto[]> {
    return httpClient.get("/projects");
  },

  createProject(projectDto: ProjectDto): Promise<ProjectDto> {
    return httpClient.post(`/curricular-units/${projectDto.curricularUnitId}/projects`, projectDto);
  },

  getProject(projectId: number): Promise<ProjectDto> {
    return httpClient.get(`/projects/${projectId}`);
  },

  updateProject(projectDto: ProjectDto): Promise<ProjectDto> {
    return httpClient.put(`/projects/${projectDto.id}`, projectDto)
  },

  deleteProject(projectId: number) {
    return httpClient.delete(`/projects/${projectId}`)
  },

  getProjectSubmissionsByProject(projectId: number): Promise<ProjectSubmissionDto[]> {
    return httpClient.get(`/submissions/project/${projectId}`)
  },

  getProjectSubmissionsByStudent(studentId: number): Promise<ProjectSubmissionDto[]> {
    return httpClient.get(`/submissions/student/${studentId}`)
  },

  getProjectSubmissionsByGroup(groupId: number): Promise<ProjectSubmissionDto[]> {
    return httpClient.get(`/submissions/group/${groupId}`)
  },

  createProjectSubmission(projectId: number, studentId: number): Promise<ProjectSubmissionDto> {
    return httpClient.post(`/project/${projectId}/student/${studentId}`)
  }
}
