export class Employee{
  constructor(
    public employeeId:number,
    public employeeCode:string,
    public firstName:string,
    public middleName:string,
    public lastName:string,
    public image:string,
    public designation:string,
    public email:string,
    public gender:string,
    public dateOfJoining:Date,
    public reportingManagerId:number,
    public employmentType:string,
    public status:string,
    public deliveryUnitId:number,
    public businessUnitId:string,
     public exitDate:Date,
    public exitRemarks:string,
    public isDeleted:string,
 
    public createdBy:number,
     public createdOn:Date,
    public modifiedBy:string,
     public modifiedOn:Date,
     public referenceDetailId: number,
     public referenceDetailValue: string
  ){}
}

export class EmployeeSaving{
  constructor(
    public employeeId:number,
    public employeeCode:string,
    public firstName:string,
    public middleName:string,
    public lastName:string,
    public image:string,
    public designation:string,
    public email:string,
    public gender:number,
    public dateOfJoining:Date,
    public reportingManagerId:number,
    public employmentType:number,
    public status:number,
    public deliveryUnitId:number,
    public businessUnitId:number,
     public exitDate:Date,
    public exitRemarks:string,
    public isDeleted:boolean,
   
    public createdBy:string,
     public createdOn:Date,
    public modifiedBy:string,
     public modifiedOn:Date,
     
  ){}
}

