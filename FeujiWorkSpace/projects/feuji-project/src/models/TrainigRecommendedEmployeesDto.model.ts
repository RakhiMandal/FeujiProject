export class TrainigRecommendedEmployeesDto {
    constructor(
        public employeeId: number,
        public employeeCode: string,
        public designition: string,
        public email: string,
        public skillName: string,
        public actualCompetency: string,
        public expectedCompetency: string,
        public actualcompetencyLevelId:number,
	    public expectedcompetencyLevelId:number,
        public competencyLevelId:number,
        public employeeName:string
    ) { }
}