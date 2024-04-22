// user.model.ts

export class User {
  constructor(
  public userId:number,
  public userEmpid:number,
  public username:string,
  public userpassword:string,
  public designation: string,
  public userEmail: string,
  public employeeStatus: string,
  public isInternal: boolean,
  public isDeleted: boolean,
  public uuid: string,
  public createdBy: string,
  public createdOn: Date,
  public modifiedBy: string,
  public modifiedOn: Date,
  public flag: boolean,

  ){}
}
