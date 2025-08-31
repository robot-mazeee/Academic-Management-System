import axios from "axios";
import { useAppearanceStore } from "@/stores/appearance";
import DeiError from "@/models/DeiError";

const httpClient = axios.create({
  baseURL: import.meta.env.VITE_ROOT_API,
  timeout: 50000,
  headers: {
    "Content-Type": "application/json",
  },
});

async function errorMessage(error: any): Promise<string> {
  if (error.message === "Network Error") {
    return "Unable to connect to the server";
  } else if (error.message.split(" ")[0] === "timeout") {
    return "Request timeout - Server took too long to respond";
  } else {
    return error.response?.data?.message ?? "Unknown Error";
  }
}

async function handleError(error: any): Promise<never> {
  const deiErr = new DeiError(
    await errorMessage(error),
    error.response?.data?.code ?? -1
  );
  const appearance = useAppearanceStore();
  appearance.pushError(deiErr);
  appearance.loading = false;
  throw deiErr;
}

httpClient.interceptors.request.use((request) => request, handleError);
httpClient.interceptors.response.use((response) => response.data, handleError);

export default httpClient;
