import { EmployeesSkillsListDto } from "./EmployeesSkillsListDto.model";

export class PaginationDto {
    constructor(
        public pageNo: number,
        public pageSize: number,
        public last: boolean,
        public first: boolean,
        public totalRecords: number,
        public totalPages: number,
        public skillList: EmployeesSkillsListDto[]
){}
}