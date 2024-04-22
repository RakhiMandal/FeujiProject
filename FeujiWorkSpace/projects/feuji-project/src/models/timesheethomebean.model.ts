export class TimesheetWeekDayBean{
  constructor(
 public employeeId:number,
 public accountProjectId: number,
 public taskTypeId: number,
 public taskId: number,

 public attendanceType:number,
// Assuming dates are strings in the form   at 'DD-Mon'
public dateMon: Date,// e.g., '26-Feb'
public dateTue: Date, // e.g., '27-Feb'
public dateWed: Date, // e.g., '28-Feb'
public dateThu: Date, // e.g., '29-Feb'
public dateFri: Date , // e.g., '01-Mar'
public dateSat: Date, 
public dateSun: Date,// e.g., '02-Mar'
public hoursMon: number,
public hoursTue: number,
public hoursWed: number,
public hoursThu: number,
public hoursFri: number,
public hoursSat: number,
public hoursSun:number,
public comments:string,
public timesheetStatus:number,
public accountId:number,

)
{}
}

export class WeekAndDayDto

{
  constructor(
      public   timesheetWeekId:number,
    public accountId:number,
      public employeeId:number,
      public accountProjectId:number,
      public  projectName: string,
      public taskTypeId:number,
      public   taskTypeName: string,
      public taskId:number,
      public   taskName:string,
  
      public    attendanceType:number,
      public    attendanceTypeName:string,
      public weekStartDate:Date,
  // Assuming dates are strings in the format 'DD-Mon'
  public    dateMon: Date ,// e.g., '26-Feb'
  public   dateTue: Date , // e.g., '27-Feb'
  public  dateWed: Date , // e.g., '28-Feb'
  public dateThu: Date , // e.g., '29-Feb'
  public dateFri: Date , // e.g., '01-Mar'
  public  dateSat: Date , 
  public  dateSun: Date ,// e.g., '02-Mar'
  public  hoursMon: number,
  public hoursTue: number,
  public  hoursWed: number,
  public  hoursThu: number,
  public hoursFri: number,
  public hoursSat: number,
  public hoursSun:number,
  public comments:string,
  public timesheetStatus:number,
  public timesheetStatusname:string)
{

}}
export class SaveAndEditRecords {
  constructor(
      public timesheetWeekDayDetailDto: TimesheetWeekDayBean[],
      public weekAndDayDto: WeekAndDayDto[]
  ){}
}