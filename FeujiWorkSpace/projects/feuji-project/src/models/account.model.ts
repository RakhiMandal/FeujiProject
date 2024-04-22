export class Account{
    constructor(
        public accountId:number,
        public  accountName:string,
        public  ownerId :number,
        public  relationshipManagerId:number,
        public  businessDevelopmentManagerId:number,
        public  parentAccountId:number,
        public  accountBuId:number,
        public  plannedStartDate :Date,
        public   plannedEndDate:Date,
        public   actualStartDate:Date,
        public   actualEndDate:Date,
        public  address:string,
        public   city:string,
        public   state:string,
        public  zipcode:string,
        public  country:string,
        public  isRed:string,
        public accountStatus:number,
        public   comments:string,
        public   isDeleted:boolean,
    //	private Character uuId;  
    
         public  createdBy:string,
         public   createdOn:Date,
         public   modifiedBy:string,
         public   modifiedOn:Date
    
    
    ){}
  }