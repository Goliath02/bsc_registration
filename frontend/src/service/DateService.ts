import axios from "axios";

export function getDate(startDate: Date, trainingUnits:number) {


    const api = axios.get("/api/dateInfo", {params: {startDate: startDate, trainingUnits: trainingUnits}});
}