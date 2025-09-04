import type PersonDto from "@/models/PersonDto";
import httpClient from "./ApiClient";
import CurricularUnitDto from "@/models/CurricularUnitDto";
import type TestDto from "@/models/TestDto";
import ProjectDto from "@/models/ProjectDto";
import type EnrollmentDto from "@/models/EnrollmentDto";

export default {
  getCurricularUnits(): Promise<CurricularUnitDto[]> {
    return httpClient.get("/curricular-units")
  },

  getCurricularUnitStudents(id: number): Promise<PersonDto[]> {
    return httpClient.get(`/curricular-units/${id}/students`)
  },

  getCurricularUnitTeachers(id: number): Promise<PersonDto[]> {
    return httpClient.get(`/curricular-units/${id}/assistant-teachers`)
  },

  getCurricularUnitMainTeacher(curricularUnitId: number): Promise<PersonDto> {
    return httpClient.get(`/curricular-units/${curricularUnitId}/main-teacher`)
  },

  getCurricularUnitPeople(curricularUnitId: number): Promise<PersonDto[]> {
    return httpClient.get(`/curricular-units/${curricularUnitId}/people`)
  },

  createCurricularUnit(curricularUnit: CurricularUnitDto): Promise<CurricularUnitDto> {
    return httpClient.post("/curricular-units", curricularUnit)
  },

  deleteCurricularUnit(curricularUnit: CurricularUnitDto) {
    return httpClient.delete(`/curricular-units/${curricularUnit.id}`)
  },

  editCurricularUnit(curricularUnit: CurricularUnitDto) {
    return httpClient.put(`/curricular-units/${curricularUnit.id}`, curricularUnit)
  },

  assignCurricularUnitStudents(id: number, students: PersonDto[]) {
    return httpClient.patch(`/curricular-units/${id}/students`, students)
  },

  assignCurricularUnitTeachers(id: number, teachers: PersonDto[]) {
    return httpClient.patch(`/curricular-units/${id}/assistant-teachers`, teachers)
  },

  removeTeachingAssistant(curricularUnitId: number, teacherId: number) {
    return httpClient.delete(`/curricular-units/${curricularUnitId}/assistant-teachers/${teacherId}`)
  },

  getCurricularUnitTests(id: number): Promise<TestDto[]> {
    return httpClient.get(`/curricular-units/${id}/tests`)
  },

  getCurricularUnitProjects(id: number): Promise<ProjectDto[]> {
    return httpClient.get(`/curricular-units/${id}/projects`)
  },

  createEnrollment(enrollmentDto: EnrollmentDto): Promise<EnrollmentDto[]> {
    return httpClient.post(`/curricular-units/${enrollmentDto.curricularUnitId}/enrollments`, enrollmentDto);
  },

  getCurricularUnitEnrollments(id: number): Promise<EnrollmentDto[]> {
    return httpClient.get(`/curricular-units/${id}/enrollments`);
  },

  deleteEnrollment(enrollmentId: number) {
    return httpClient.delete(`/enrollments/${enrollmentId}`);
  }
};