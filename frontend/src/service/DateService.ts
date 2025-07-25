import {apiClient} from "@/apiClient";

export function getHolidayInfo(startDate: Date, trainingUnits:number) {


    return  apiClient.get("/api/course/holidayDateInfo", {params: {
            startDate: startDate,
            trainingUnits: trainingUnits
        }});
}