import { Injectable } from "@angular/core";
import { Account } from "./account.model";
import { AccountserviceService } from "./accountservice.service";




@Injectable({providedIn: 'root'})
export class AccountRepo{

  public account:Account[]=[];


  constructor(private dataSource:AccountserviceService) {
     this.getAccount();
   }
   getAccount(){
    this.dataSource.getAccount().subscribe(data=>{

     console.log(data);
     console.log("asdfghjkl");
     
     this.account=data;
     console.log(this.account);
     console.log("asdfghjkl");
   })
 
  console.log("asdfghjklkjhgfdsasdfghjkjhgfdsasdf");
  
    
  }


  sendAccount(account:Account){
    this.dataSource.saveAccount(account).subscribe(
      (data)=>{
       console.log(data);
     },
    (error)=>{
     console.error("error no data found");
    }
    )
  }
}
