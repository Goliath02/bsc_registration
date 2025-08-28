// src/apiClient.ts
import axios from "axios";

export const apiClient = axios.create({
  baseURL: getTargetURL(),
  withCredentials: true,
});

export function getTargetURL() {
  return import.meta.env.DEV ? "http://localhost:8080" : "";
}
