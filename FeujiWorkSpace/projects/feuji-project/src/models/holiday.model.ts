export class Holiday{
  constructor(
      public  holidayId:number,

      public  holidayDate:Date,
  
      public  holidayDay:String,
  
      public  holidayName:String,
  
      public  description:String,
  
      public  location:String,
  
      public  isDeleted:boolean,
  
       public  uuid:String,
  
       public   createdBy:String,
  
       public  createdOn:Date,
  
      public   modifiedBy:String,
  
        public  modifiedOn:Date
  
  
  
  
  ){}
  }