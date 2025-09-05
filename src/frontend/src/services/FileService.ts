import httpClient from "./ApiClient";

export default class FileService {
  static async uploadFile(file: File): Promise<string> {
    const formData = new FormData();
    formData.append("file", file);

    const response = await httpClient.post(`files/upload`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return response.data;
  }

  static async downloadFile(fileName: string): Promise<Blob> {
    const response = await httpClient(`files/download/${fileName}`, {
      responseType: "blob",
    });
    return response.data;
  }
}
