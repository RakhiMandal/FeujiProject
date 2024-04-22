export class Skill{
  constructor(
    public skillName:string,
    public techinicalCategoryId:number,
    public skillCategoryId:number,
    public description:string,
    public status:number,
    public createdBy:string,
    public modifiedBy:string
    ){}
  }