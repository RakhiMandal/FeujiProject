export class EmployeeSkillObject{
    constructor( 
	    public firstName:string,  
    public  middleName:string,
    public  lastName:string,
	public  employeeId:number,
	public  email:string,
	public  designation:string,
	public  skillName:string,
	public  description:string,
	public  exCompetencyLevelId:number,
	public  acCompetencyLevelId:number,
	public  exReferenceDetailsValues:string,	
	public  acReferenceDetailsValues:string,
    public  skillCategoryName:string,
    public  subSkillCategoryName:string
){}
}