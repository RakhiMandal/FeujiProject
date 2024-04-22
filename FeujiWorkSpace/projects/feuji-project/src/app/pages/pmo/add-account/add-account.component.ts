import { Component } from '@angular/core';
import { Account } from '../../../../models/account.model';
import { AccountserviceService } from '../../../../models/accountservice.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrl: './add-account.component.css'
})
export class AddAccountComponent {

  public account:any=Account;
 acc:Account=new Account(0,'',0,0,0,0,0,new Date(),new Date(),new Date(),new Date(),'','','','',''

 ,'',0,'','','','',new Date(),'',new Date())

 constructor(private accountService: AccountserviceService) {}
 sendAccount(acc:any ) {
  console.log(acc);
  this.accountService.saveAccount(acc).subscribe(res=>this.account=res);
  console.log("inserted");

 }

}
