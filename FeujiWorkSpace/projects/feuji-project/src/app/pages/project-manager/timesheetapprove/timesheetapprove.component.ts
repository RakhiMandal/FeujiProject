import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { timesheetWeekApproval } from '../../../../models/timesheet-week-approval.model';
import { TimesheetWeekApprovalService } from '../../../../models/timesheet-week-approval.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-timesheetapprove',
  templateUrl: './timesheetapprove.component.html',
  styleUrls: ['./timesheetapprove.component.css'] // corrected 'styleUrl' to 'styleUrls'
})
export class TimesheetapproveComponent implements OnInit {
  timesheets: any[] = [];
  public weekTimeSheet: any = [];
  public timesheetData: any[] = [];
  public account: any[] = [];
  public months: string[] = ['All', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  public years: number[] = [];
  public accountName: string[] = [];
  public selectedMonth: string = '';
  public selectedYear: number;
  public year: number = 2024;
  searchText: any = '';
  public accounts: any[] = [];
  public employee: any[] = [];
  public accountId: number = 0;
  public empId: number = 0;

  public userEmpId: number = 0;
  selectedAccount: any;

  constructor(private timesheetService: TimesheetWeekApprovalService, private router: Router) {
    const currentDate = new Date();
    this.selectedMonth = this.months[currentDate.getMonth() + 1];
    this.selectedAccount = 0;
    this.selectedYear = currentDate.getFullYear();

    const userData = JSON.parse(localStorage.getItem('user') || '{}');
    this.userEmpId = userData.userEmpId || 0;
  }

  ngOnInit(): void {
    this.getAllTimesheets(this.userEmpId);
    this.timesheetService.getAccounts(this.userEmpId).subscribe(
      (resp) => {
        this.accounts = resp as any[];
      },
      (error) => {
        console.error(error);
      }
    );
  }

  getAllTimesheets(approvedBy: number): void {
    this.timesheetService.getAllTimesheets(approvedBy)
      .subscribe(
        (data) => {
          this.weekTimeSheet = data;

          console.log('Fetched timesheets:', this.weekTimeSheet);
        console.log('EmployeeId in fetched data:', this.weekTimeSheet[0]?.employeeId); // Log employeeId
        },
        (error) => {
          console.error('Error fetching timesheets:', error);
        }
      );
  }

  get filteredEmployees() {
    return this.employee.filter(emp => emp.firstName.toLowerCase().includes(this.searchText.toLowerCase()));
  }

  OnSelectAccountByAccountId(event: any) {
    const selectedAccountId = event.target.value;

    if (selectedAccountId !== null && selectedAccountId !== undefined) {
      this.accountId = Number(selectedAccountId);
      this.selectedMonth = event.target.value;
      this.timesheetService.getProjectsByAccountId(this.accountId, this.userEmpId)
        .subscribe(
          (resp) => {
            this.weekTimeSheet = resp;
          },
          (error) => {
            console.error('Error fetching timesheets:', error);
          }
        );
    } else {
      console.error('Selected account ID is null or undefined.');
    }
  }

  OnSelectAccountByMonth(event: any) {
    this.selectedMonth = event.target.value;

    this.timesheetService.fetchData(this.selectedMonth, this.year, this.accountId)
      .subscribe(
        (resp) => {
          console.log(this.selectedMonth);
          console.log(resp);
          this.weekTimeSheet = resp;
        },
        (error) => {
          console.error(error);
        }
      );
  }

  OnSelectAccount(event: any) {
    if (!this.selectedAccount || !this.selectedMonth) {
      alert('Please select Account and Month before searching.');
      return;
    }

    const month1 = this.selectedMonth;
    this.timesheetService.getProjects(this.userEmpId, month1, this.year, this.accountId, this.empId)
      .subscribe(
        (resp) => {
          console.log(resp);
          this.weekTimeSheet = resp;
        },
        (error) => {
          console.error(error);
        }
      );

    return this.employee.filter(emp => emp.firstName.toLowerCase().includes(this.searchText.toLowerCase()));
  }






  // goToView(weekTimesheet: timesheetWeekApproval) {
  //   weekTimesheet.employeeId = this.empId;
  //   weekTimesheet.accountId = this.accountId;

  //   console.log("Updated empId: ", weekTimesheet.employeeId); // Debugging
  //   console.log("Updated accountId: ", weekTimesheet.accountId); // Debugging

  //   this.router.navigate(['/manager/dailyStatus'], { state: { weekTimesheet: weekTimesheet } });
  // }

  goToView(weekTimesheet:any ) {
    if (!this.accountId) {
      // No account selected, display SweetAlert
      Swal.fire({
        icon: 'warning',
        title: 'Please select an account first',
        showConfirmButton: false,
        timer: 1500 // Close the alert after 1.5 seconds
      });
      return; // Stop further execution
    }
  
    // Account selected, proceed with navigation
    weekTimesheet.accountId = this.accountId;
    this.empId = weekTimesheet.employeeId;

   

    this.router.navigate(['/manager/dailyStatus'], { state: { weekTimesheet: weekTimesheet } });
  }


  // onEmployeeClick(employee: any) {
  //   console.log(employee);

  //   this.empId = employee.employeeId; // Assuming the employee object has an 'employeeId' property
  // }



}