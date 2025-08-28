import { apiClient } from "@/apiClient";

export async function getCourses() {
  return apiClient.get("/api/course/courses");
}

export async function getTrainers() {
  return apiClient.get("/api/info/trainers");
}

export async function getTrainingPlaces() {
  return apiClient.get("/api/info/places");
}
