import RevisionDto from "@/models/RevisionDto";
import httpClient from "./ApiClient";

export default {
  getRevisions(): Promise<RevisionDto[]> {
    return httpClient.get("/revisions");
  },

  getRevisionByStudentAndTest(studentId: number, testId: number): Promise<RevisionDto> {
    return httpClient.get(`/revisions/student/${studentId}/test/${testId}`)
  },

  getRevisionsByStudent(studentId: number): Promise<RevisionDto[]> {
    return httpClient.get(`/revisions/student/${studentId}`)
  },

  createRevision(revisionDto: RevisionDto): Promise<RevisionDto> {
    return httpClient.post('/revisions', revisionDto)
  },

  updateStatus(revision: RevisionDto): Promise<RevisionDto> {
    return httpClient.patch(`/revisions/${revision.id}`, revision)
  }
};