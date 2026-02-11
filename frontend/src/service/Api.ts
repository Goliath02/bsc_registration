// services/api.ts
import axios, {
  AxiosInstance,
  AxiosError,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from "axios";
import { getTargetURL } from "@/apiClient";
import { useAppState } from "@/service/AppStateService";

const api: AxiosInstance = axios.create({
  baseURL: getTargetURL(),
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    const { setLoading } = useAppState();
    setLoading(true);
    return config;
  },
  (error: AxiosError): Promise<AxiosError> => {
    const { setError } = useAppState();
    setError(true);
    return Promise.reject(error);
  },
);

api.interceptors.response.use(
  (response: AxiosResponse): AxiosResponse => {
    const { setLoading } = useAppState();
    setLoading(false);
    return response;
  },
  (error: AxiosError): Promise<AxiosError> => {
    const { setError } = useAppState();

    if (error.code === "ECONNABORTED" || error.message === "Network Error") {
      setError(true, "Backend ist nicht erreichbar");
    } else if (error.response) {
      setError(true, `Fehler: ${error.response.status}`);
    } else {
      setError(true, "Ein Fehler ist aufgetreten");
    }

    return Promise.reject(error);
  },
);

export default api;
