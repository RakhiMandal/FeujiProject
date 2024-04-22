export class Accountproject{
    constructor(
        public accountProjectId:number,
        public  projectAId:string,
        public  projectName :string,
        public  accountId:number,
        public  priority:number,
        public  projectManagerId:number,
        public  noOfBillingHours :number,
        public   plannedStartDate:Date,
        public   plannedEndDate:Date,
        public actualStartDate:Date,
        public   actualEndDate:Date,
        public projectStatus:number,
        public isActive:boolean,
        public isRed:boolean,
        public isDeleted:boolean,
        public createdBy:string,
        public createdOn:Date,
        public modifiedBy:string,
        public modifiedOn:Date


    ){}
}
