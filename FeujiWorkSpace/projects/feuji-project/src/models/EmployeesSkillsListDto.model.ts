import { SkillsBean } from "./SkillsBean.model";

export class EmployeesSkillsListDto {
    constructor(
        public employeeName: string,
        public employeeId: number,
        public employeeCode:string,
        public designition:string,
        public email:string,
        public skillLists: SkillsBean[]
    ) { }
}