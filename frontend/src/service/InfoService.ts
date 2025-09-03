import { apiClient } from "@/apiClient";

export async function getTrainers() {
  return apiClient.get("/api/info/trainers");
}

export async function getTrainingPlaces() {
  return apiClient.get("/api/info/places");
}

export async function getCourseTypes() {
  return apiClient.get("/api/info/courseTypes");
}
