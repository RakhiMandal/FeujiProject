import { TimesheetWeekDayBean, WeekAndDayDto } from "./timesheethomebean.model";

export class SaveAndEditRecords {
    constructor(
        public TimesheetWeekDayBean: TimesheetWeekDayBean[],
        public WeekAndDayDto: WeekAndDayDto[]
    ){}
}