import RevisionDto from "@/models/RevisionDto";
import httpClient from "./ApiClient";
import type RevisionHistoryDto from "@/models/RevisionHistoryDto";

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

  getRevisionHistory(revisionId: number): Promise<RevisionHistoryDto[]> {
    return httpClient.get(`/revisions/${revisionId}/history`)
  },

  createRevision(revisionDto: RevisionDto): Promise<RevisionDto> {
    return httpClient.post('/revisions', revisionDto)
  },

  updateStatus(revision: RevisionDto): Promise<RevisionDto> {
    return httpClient.patch(`/revisions/${revision.id}`, revision)
  }
};