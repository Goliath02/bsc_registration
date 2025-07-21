import axios from "axios";

export function getHolidayInfo(startDate: Date, trainingUnits:number) {


    const api = axios.get("/api/course/holidayDateInfo", {params: {startDate: startDate, trainingUnits: trainingUnits}});

    console.log("dates", api)

    return api;
}