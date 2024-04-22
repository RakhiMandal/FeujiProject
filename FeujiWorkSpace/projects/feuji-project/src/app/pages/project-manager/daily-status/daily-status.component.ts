import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { timesheetWeekApproval } from '../../../../models/timesheet-week-approval.model';
import { TimesheetWeekApprovalService } from '../../../../models/timesheet-week-approval.service';
import {
  TimesheetWeekDayBean,
  WeekAndDayDto,
} from '../../../../models/timesheethomebean.model';
import { TimesheetHomeService } from '../../../../models/timesheetHomeService.service';

@Component({
  selector: 'app-daily-status',
  templateUrl: './daily-status.component.html',
  styleUrl: './daily-status.component.css',
})
export class DailyStatusComponent implements OnInit {
  weekTimesheet: any;
  data: any;
  employeeName: string = '';
  Status: string = '';
  designation: string = '';
  plannedStartDate: Date = new Date();
  plannedEndDate: Date = new Date();
  email: string = '';
  public currentWeek1: Date[] = [];
  clicked = false;
  totalHours: any;
  isTimesheetApproved: boolean = true;
  weekTimeSheet: any;
  accountId: number = 0;
  
  timesheetService: any;
  userEmpId: any;
  year: any;


  public employee: any[] = [];

  constructor(
    private timesheetHomeService: TimesheetHomeService,
    private timesheetWeekApprovalService: TimesheetWeekApprovalService,
    private datePipe: DatePipe,
    private router: Router
  ) {}
  tasks = [
    {
      project: '',
      taskType: '',
      task: '',
      attendanceType: '',
      days: Array(7).fill(0),
    },
  ];
  defaultAccountId: number = 0;
  selectedAccount: number = 0;
  currentDate: Date = new Date();
  currentWeek: { startDate: string | null; endDate: string | null }[] = [];
  accounts: any[] = [];
  projects: any[] = [];
  projectTaskType: any[] = [];
  projectTask: any[] = [];
  attendanceTypeArr: any[] = [];


  startDate: any = '';

  lastDate: any = '';

  selectedProjectId: number = 0;
  selectedProjecttaskId: number = 0;
  selectedAttendanceType: any;
  selectedTaskId: number = 0;

  everyRowRecord: any[] = [];

  selectedTasks: any[] = [];

  rownum: number = 1;
  current: number = 0;

  valuee: number = 0;

 ngOnInit(): void {
    console.log('history.state.weekTimesheet:', history.state.weekTimesheet);

    if (history.state.weekTimesheet) {
      this.weekTimesheet = history.state.weekTimesheet;

      // const firstTimesheet = history.state.weekTimesheet;
      // this.weekTimesheet = new timesheetWeekApproval(


      //   firstTimesheet.employeeId,
      //   firstTimesheet.designation,
      //   firstTimesheet.employeeCode,
      //   firstTimesheet.firstName,
      //   firstTimesheet.email,
      //   firstTimesheet.approvedBy,
      //   firstTimesheet.weekNumber,
      //   firstTimesheet.projectName,
      //   firstTimesheet.accountProjectId,
      //   firstTimesheet.totalBillingHours,
      //   firstTimesheet.totalNonBillingHours,
      //   firstTimesheet.totalLeaveHours,
      //   firstTimesheet.timesheetStatus,
      //   new Date(firstTimesheet.weekStartDate),
      //   new Date(firstTimesheet.weekEndDate),
      //   new Date(firstTimesheet.plannedStartDate),
      //   new Date(firstTimesheet.plannedEndDate),
      //   firstTimesheet.accountId,
      //   firstTimesheet.reportingManagerId
      // );
      console.log(this.weekTimesheet);





      // Calculate currentWeek1
      const startDate1 = new Date(this.weekTimesheet.weekStartDate);
      const endDate = new Date(this.weekTimesheet.weekEndDate);
      this.currentWeek1 = [];

      // Iterate over each day between startDate and endDate
      let currentDate1 = new Date(startDate1);
      while (currentDate1 <= endDate) {
        this.currentWeek1.push(new Date(currentDate1));
        currentDate1.setDate(currentDate1.getDate() + 1);
        const formattedDateString = this.formattedDate(currentDate1);
        // Use formattedDateString as needed
      }
      console.log('Assigned weekTimesheet:', this.weekTimesheet);
      this.fetchWeekDayData();
      this.getTotalHours();

      this.employeeName = this.weekTimesheet.fullName;
      this.Status = this.weekTimesheet.timesheetStatus;
      this.designation = this.weekTimesheet.designation;
      this.email = this.weekTimesheet.email;
      this.plannedStartDate = this.weekTimesheet.plannedStartDate;
      this.plannedEndDate = this.weekTimesheet.plannedEndDate;

      console.log('employee name' + this.employeeName);
      console.log('employee name' + this.Status);
      this.timesheetApprove(this.weekTimesheet);
    } else {
      console.error('No data found in history.state.weekTimesheet');
    }
  }
  initializeValues() {
    this.employeeName = this.weekTimesheet.fullName;
    this.Status = this.weekTimesheet.timesheetStatus;
    this.designation = this.weekTimesheet.designation;
    this.email = this.weekTimesheet.email;
    this.plannedStartDate = this.weekTimesheet.plannedStartDate;
    this.plannedEndDate = this.weekTimesheet.plannedEndDate;
  }
  updateStatus(newStatus: string) {
    this.Status = newStatus; // Update the Status property with newStatus
  }


  ngAfterViewChecked() {
    this.columnsumnew();
  }


  formattedDate(date: Date | null): string {
    if (!date) {
      return '';
    }
    return this.datePipe.transform(date, 'dd-MMM EEE') || '';
  }



  totalvalue: number[] = [0, 0, 0, 0, 0, 0, 0];




  columnsumnew() {
    for (let columnCount = 4; columnCount < 11; columnCount++) {
      let sum: number = 0;

      for (let rowCount = 0; rowCount < this.limitRow; rowCount++) {
        const inputValue = (
          document.getElementById(
            'data_' + rowCount + columnCount
          ) as HTMLInputElement
        ).innerText;
        sum += Number(inputValue);
      }
      this.totalvalue[columnCount - 4] = sum;
    }
    this.rowsumnew();
    return this.totalvalue;
  }
  rowsumnew() {
    for (let rowCount = 0; rowCount < this.limitRow; rowCount++) {
      let sum: number = 0;
      for (let columnCount = 4; columnCount < 11; columnCount++) {
        const inputValue = (
          document.getElementById(
            'data_' + rowCount + columnCount
          ) as HTMLInputElement
        ).innerText;
        //console.log(' data_' + rowCount + columnCount + " value "+inputValue)

        sum += Number(inputValue);
      }
      (
        document.getElementById('data_' + rowCount + 11) as HTMLInputElement
      ).innerText = String(sum);
    }
  }

  columnsum() {
    console.log(this.rownum);
    for (let rowCount = 0; rowCount < this.rownum; rowCount++) {

      let sum: number = 0;
      for (let columnCount = 0; columnCount < 7; columnCount++) {
        console.log('Input ' + columnCount + ' - ' + rowCount);
        const inputValue = (
          document.getElementById(
            'input_' + rowCount + columnCount
          ) as HTMLInputElement
        ).value;
        this.totalvalue[columnCount] += Number(inputValue);
      }
      this.rowsum(rowCount);
    }

    return this.totalvalue;
  }

  rowsum(count: number) {
    let sum: number = 0;
    for (let columnCount = 0; columnCount < 7; columnCount++) {
      const inputValue = (
        document.getElementById(
          'input_' + count + columnCount
        ) as HTMLInputElement
      ).value;
      sum += Number(inputValue);

      this.everyRowRecord[(this.rownum, 12 + columnCount)] =
     Number(inputValue);
     }
     (
      document.getElementById('input_' + count +7 ) as HTMLInputElement
     ).value = String(sum);

    }


  timesheetStatus: any[] = [];


  allRows: TimesheetWeekDayBean[] = [];




  addDataToAllarows() {
    let timesheetWeekDayBean: any = new TimesheetWeekDayBean(
      0,
      0,
      0,
      0,
      0,
      new Date(),
      new Date(),
      new Date(),
      new Date(),
      new Date(),
      new Date(),
      new Date(),
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      '',
      0,
      0
    );
    timesheetWeekDayBean.employeeId = this.everyRowRecord[0];
    timesheetWeekDayBean.projectId = this.everyRowRecord[1];
    timesheetWeekDayBean.taskId = this.everyRowRecord[3];
    timesheetWeekDayBean.taskTypeId = this.everyRowRecord[2];
    timesheetWeekDayBean.attendanceType = this.everyRowRecord[4];
    timesheetWeekDayBean.dateMon = this.everyRowRecord[5];

    timesheetWeekDayBean.dateTue = this.everyRowRecord[6];

    timesheetWeekDayBean.dateWed = this.everyRowRecord[7];

    timesheetWeekDayBean.dateThu = this.everyRowRecord[8];

    timesheetWeekDayBean.dateFri = this.everyRowRecord[9];

    timesheetWeekDayBean.dateSat = this.everyRowRecord[10];

    timesheetWeekDayBean.dateSun = this.everyRowRecord[11];

    timesheetWeekDayBean.hoursMon = this.everyRowRecord[12];
    timesheetWeekDayBean.hoursTue = this.everyRowRecord[13];
    timesheetWeekDayBean.hoursWed = this.everyRowRecord[14];
    timesheetWeekDayBean.hoursThu = this.everyRowRecord[15];
    timesheetWeekDayBean.hoursFri = this.everyRowRecord[16];
    timesheetWeekDayBean.hoursSat = this.everyRowRecord[17];
    timesheetWeekDayBean.hoursSun = this.everyRowRecord[18];
    timesheetWeekDayBean.comments = this.everyRowRecord[19];
    timesheetWeekDayBean.timesheetStatus = this.everyRowRecord[20];
    timesheetWeekDayBean.accountId = this.everyRowRecord[21];
    this.allRows[this.rownum - 1] = timesheetWeekDayBean;
    console.log('allrows' + this.allRows);
  }

  limitRow: number = 0;
  fetchedDetails: WeekAndDayDto[] = [];
  deetails: WeekAndDayDto[] = [];

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = date.toLocaleDateString('en-US', { month: 'short' });

    const day = ('0' + date.getDate()).slice(-2);

    return `${day}-${month}-${year}`;
  }

  fetchWeekDayData(): void {

    console.log("Employee ID: " + this.weekTimesheet.employeeId);
    console.log("h2");





    const startDate1 =
      this.datePipe.transform(
        this.weekTimesheet.weekStartDate,
        'dd-MMM-yyyy'
      ) || '';


    const lastDate =
      this.datePipe.transform(this.weekTimesheet.weekEndDate, 'dd-MMM-yyyy') ||
      '';



    this.timesheetHomeService
      .getWeekDayDetails(
        this.weekTimesheet.accountId,
        this.weekTimesheet.employeeId,
        startDate1,
        lastDate
      )
      .subscribe((fetched) => {
        this.fetchedDetails = fetched as WeekAndDayDto[];
        this.limitRow = fetched.length;
        console.log('Limit Row ' + this.limitRow);
      });

  }

  loadTimesheetData(): void {

    this.fetchWeekDayData();
  }

  convertedDate: string = '';

  getTotalHours(): void {
    this.timesheetWeekApprovalService
      .getTotalHours(
        this.weekTimesheet.employeeId,
        this.weekTimesheet.accountProjectId,
        this.weekTimesheet.weekNumber
      )
      .subscribe(
        (totalHours) => {
          this.totalHours = totalHours;
          console.log('Total hours data:', totalHours);
        },
        (error) => {
          console.error('Error fetching total hours:', error);
        }
      );
  }

  timesheetApprove(weekTimesheet: timesheetWeekApproval) {
    if (this.Status === 'Approved') {
      this.isTimesheetApproved = true;
    } else {
      this.isTimesheetApproved = false;
    }
    const startDate1 =
      this.datePipe.transform(
        this.weekTimesheet.weekStartDate,
        'dd-MMM-yyyy'
      ) || '';
    console.log(startDate1);
    this.timesheetHomeService
      .updateTimesheetStatus(
        this.weekTimesheet.employeeId,
        this.weekTimesheet.accountId,
        startDate1
      )
      .subscribe((data) => {
        this.data = data;
        this.router.navigate(['/DailyStatusComponent']);
      });
      this. loadTimesheetData();
  }

  rejectTimesheet(weekTimesheet: timesheetWeekApproval) {
    if (this.Status === 'Rejected') {
      this.isTimesheetApproved = true;
    } else {
      this.isTimesheetApproved = false;
    }
    const startDate1 =
      this.datePipe.transform(
        this.weekTimesheet.weekStartDate,
        'dd-MMM-yyyy'
      ) || '';

    this.timesheetHomeService
      .rejectTimesheetStatus(
        this.weekTimesheet.employeeId,
        this.weekTimesheet.accountId,
        startDate1
      )
      .subscribe((data) => {
        this.data = data;
        this.router.navigate(['/DailyStatusComponent']);
      });
  }

  isRejectButtonDisabled(): boolean {
    return this.isTimesheetApproved || this.Status === 'Rejected';
  }

  closeTheView(weekTimesheet: timesheetWeekApproval) {
    weekTimesheet.accountId = 2;
    console.log('account' + this.accountId);

    weekTimesheet.employeeId = 108;
    console.log(weekTimesheet);

    this.router.navigate(['/manager/timesheet-approval'], {
      state: { weekTimesheet: weekTimesheet },
    });
    console.log('state' + weekTimesheet);
    this.OnSelectAccountByAccountId(event);
  }

  OnSelectAccountByAccountId(event: any) {
    const selectedAccount = event.target.value;
    console.log(selectedAccount);
    this.accountId = 2;
  //  alert(this.selectedAccount);
    this.timesheetService
      .getProjectsByAccountId(this.userEmpId, this.year, this.accountId)
      .subscribe(
        (resp: any) => {
          alert('getting respose');
          this.weekTimeSheet = resp;

          console.log(this.weekTimeSheet);
          console.log(resp);
        },
        (error: any) => {
          console.error(error);
        }
      );
    this.getEmployee();
  }

  getEmployee() {
    this.selectedAccount;
    this.timesheetService
      .getAllEmployee(this.userEmpId, this.selectedAccount)
      .subscribe((data: any[]) => {
        this.employee = data;
      });
  }
}

