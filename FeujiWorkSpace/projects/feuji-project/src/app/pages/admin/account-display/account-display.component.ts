import { Component, OnInit } from '@angular/core';
import { AccountserviceService } from '../../../../models/accountservice.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-account-display',
  templateUrl: './account-display.component.html',
  styleUrl: './account-display.component.css'
})
export class AccountDisplayComponent implements OnInit {
  public account:any[]=[];
  constructor(private accountService: AccountserviceService, private router: Router ){}
  ngOnInit(){
    console.log("loaded");
    this.getAccount();
  }
  getAccount(){
    this.accountService.getAccount().subscribe(data=>{
     console.log(data);
     this.account=data;
     console.log(this.account);
   })

}

editItem(account: any) {
  console.log("id...................",account);
 
  this.router.navigate(['/admin/update-account'],{state: {account} });
}
removeTask(accountId:number) {
  Swal.fire({
    title: 'Are you sure?',
    text: 'You will not be able to recover this holiday!',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Yes, delete it!',
    cancelButtonText: 'No, keep it'
  }).then((result) => {
    if (result.isConfirmed) {
      console.log("component deleted", accountId);
      this.accountService.deleteEmployee(accountId).subscribe(res => {
        console.log(res);
        // Handle success message or any other action after deletion
        this.getAccount();
      });
    } else if (result.dismiss === Swal.DismissReason.cancel) {
      // Handle cancel action
      // No deletion occurred
    }
  });

}
}
