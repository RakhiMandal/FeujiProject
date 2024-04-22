import { Component, OnInit } from '@angular/core';
import { Account } from '../../../../models/account.model';
import { AccountserviceService } from '../../../../models/accountservice.service';


@Component({
  selector: 'app-account-add',
  templateUrl: './account-add.component.html',
  styleUrl: './account-add.component.css'
})
export class AccountAddComponent implements OnInit{


  public account:any=Account;
  public owenrId: any[] = [];
  public businessUnitType: any[] = [];
  public statusTypes:any[]=[];

  public account1:any[]=[];
  ngOnInit(): void {
    this.getEmployeeName();
    this.getStatusType();
    this.getBusinessUnitType();
    this.getAccount();
    // this.getparentId(name);
  }
 acc:Account=new Account(0,'',0,0,0,0,0,new Date(),new Date(),new Date(),new Date(),'','','','',''
 ,'',0,'',false,'',new Date(),'',new Date())

 constructor(private accountService: AccountserviceService) {}
 sendAccount() {
  // alert("entrinf")
  console.log(this.acc);
  this.accountService.saveAccount(this.acc).subscribe(res=>this.account=res);

 }
 getAccount(){
  this.accountService.getAccount().subscribe(data=>{
   console.log(data);
   this.account1=data;
   console.log(this.account1);
 })

}

 getEmployeeName(): void {

  this.accountService.getEmployeeName().subscribe(
    (data) => {
      this.owenrId = data;
    },
    error => {
      console.error('Error occurred while fetching account names:', error);
    }
  );
}



getBusinessUnitType() {
  this.accountService.getBusinessUnitType().subscribe((businessUnit:any[]) => {
    console.log(businessUnit+":::::::::");
    this.businessUnitType = businessUnit;
  });
}
getStatusType() {
  this.accountService.getStatusType().subscribe((status:any[]) => {
    console.log(status);
    console.log("status:::::::::");

    this.statusTypes = status;
  });
}
}
