export class EmployeeSkillDto{
    constructor(
        public employeeName:string,
        public employeeId:number,
        public skillId:number,
        public skillName:string,
        public employeCompetencyLevel:number
    ){

    }
}