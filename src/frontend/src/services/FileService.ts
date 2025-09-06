import httpClient from "./ApiClient"

export default class FileService {
  static async uploadFile(file: File) {
    const formData = new FormData()
    formData.append("file", file)

    const response = await httpClient.post(`files/upload`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })

    return response
  }

  static async downloadFile(fileName: string): Promise<Blob> {
    return httpClient.get(`/files/download/${fileName}`, { responseType: 'blob' })
  }

  static async assignTestSheet(testSheet: string, testId: number) {
    return httpClient.patch(`/tests/${testId}/test-sheet`, testSheet)
  }

  static async assignProjectSheet(projectSheet: string, projectId: number) {
    return httpClient.patch(`/projects/${projectId}/project-sheet`, projectSheet)
  }

  static async assignTestGradeCorrection(correction: string, testGradeId: number) {
    return httpClient.patch(`/test-grades/${testGradeId}/correction`, correction)
  }

  static async assignProjectSubmission(submission: string, projectId: number) {
    return httpClient.patch(`/project-submissions/${projectId}/submission`, submission)
  }
}
