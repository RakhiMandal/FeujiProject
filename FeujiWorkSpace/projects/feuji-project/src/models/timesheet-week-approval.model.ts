

export class timesheetWeekApproval{
    constructor(

        public TimesheetWeekSummaryId:number=2,
        public employeeId:number,
        public designation:string,
        public employeeCode:string,
        public fullName:string,
        public email:string,
        public approvedBy:number,
        public weekNumber:number,
        public projectName:string,
        public accountProjectId:number,
        public totalBillingHours:number,
        public totalNonBillingHours:number,
        public totalLeaveHours:number,
        public timesheetStatus:string,
        public weekStartDate:Date,
        public weekEndDate:Date,
        public plannedStartDate:Date,
        public plannedEndDate:Date,
        public accountId:number
    ){}
    }


