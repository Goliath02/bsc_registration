import {apiClient} from "@/apiClient";

export async function getHolidayInfo(startDate: Date, trainingUnits: number) {

    const res = await apiClient.get("/api/course/holidayDateInfo", {
        params: {
            startDate: startDate,
            trainingUnits: trainingUnits
        }
    });
    return res.data;
}

export type TrainingUnitsDto = {
    dates: Date[];
    numberOfTrainingUnits: number;
    holidayDates: HolidayDateInfo[];
}

export type HolidayDateInfo = {
    id: number;
    holidayName: string;
    fromDate: Date;
    toDate: Date;
}