import {apiClient} from "@/apiClient";

export interface Trainer {
    trainerId: number,
    trainerName: string
}


export async function getTrainers(): Promise<Trainer[]> {
    const response = await apiClient.get<Trainer[]>("/api/info/trainers")
    return response.data
}

export const getTrainerById = (trainerId: number, availableTrainers: Trainer[]) : Trainer | undefined => {
    return availableTrainers.find(trainer => trainer.trainerId === trainerId);
}


export interface Place {
    "id": number,
    "name": string,
    "street": string,
    "houseNumber": number,
    "streetNumberAddition": string,
    "city": string,
    "state": string,
    "country": string,
    "postalCode": string,
}

export async function getTrainingPlaces(): Promise<Place[]> {
    const response = await apiClient.get<Place[]>("/api/info/places");
    return response.data;
}

export const getTrainingPlaceById = (placeId: number, trainingPlaces: Place[]) : Place | undefined => {
    return trainingPlaces.find(place => place.placeId === placeId);
}

export interface CourseType {
    courseTypeId: number,
    courseTypeName: string,
}

export async function getCourseTypes(): Promise<CourseType[]> {
    const response = await apiClient.get<CourseType[]>("/api/info/courseTypes");
    return response.data;
}

export const gerCourseTypeById = (courseTypeId: number, courseTypes: CourseType[]) : CourseType | undefined => {
    return courseTypes.find(courseType => courseType.courseTypeId === courseTypeId);
}
