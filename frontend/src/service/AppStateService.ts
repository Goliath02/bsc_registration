import { ref, Ref } from "vue";

export interface AppState {
  isLoading: boolean;
  hasError: boolean;
  error: string;
}

export interface ApiError {
  code?: string;
  message?: string;
  response?: {
    status: number;
    data: any;
  };
}

export interface InitialData {
  userProfile?: any;
  config?: any;
}

interface UseAppStateReturn {
  isLoading: Ref<boolean>;
  hasError: Ref<boolean>;
  errorMessage: Ref<string>;
  setLoading: (loading: boolean) => void;
  setError: (error: boolean, message?: string) => void;
  reset: () => void;
}

const isLoading = ref<boolean>(true);
const hasError = ref<boolean>(false);
const errorMessage = ref<string>("");

export function useAppState(): UseAppStateReturn {
  const setLoading = (loading: boolean): void => {
    isLoading.value = loading;
  };

  const setError = (
    error: boolean,
    message: string = "Backend nicht erreichbar",
  ): void => {
    hasError.value = error;
    errorMessage.value = message;
    if (error) isLoading.value = false;
  };

  const reset = (): void => {
    isLoading.value = false;
    hasError.value = false;
    errorMessage.value = "";
  };

  return {
    isLoading,
    hasError,
    errorMessage,
    setLoading,
    setError,
    reset,
  };
}
