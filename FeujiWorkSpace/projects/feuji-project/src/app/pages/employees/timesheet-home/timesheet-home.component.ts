import { Component, OnInit } from '@angular/core';
import { AfterViewChecked } from '@angular/core';

import { DatePipe } from '@angular/common';
import { TimesheetHomeService } from '../../../../models/timesheetHomeService.service';
import {
  SaveAndEditRecords,
  WeekAndDayDto,
} from '../../../../models/timesheethomebean.model';

import { TimesheetWeekDayBean } from '../../../../models/timesheethomebean.model';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-timesheet-home',
  templateUrl: './timesheet-home.component.html',
  styleUrl: './timesheet-home.component.css',
})
export class TimesheetHomeComponent implements OnInit, AfterViewChecked {
  constructor(
    private timesheetHomeService: TimesheetHomeService,
    private datePipe: DatePipe
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
  saveAndEditRecords: SaveAndEditRecords = new SaveAndEditRecords([], []);
  lastDate: any = '';
  holidays: any[] = [];
  selectedProjectId: number = 0;
  selectedProjecttaskId: number = 0;
  selectedAttendanceType: any;
  selectedTaskId: number = 0;
  employeeId: number = 108;
  everyRowRecord: any[] = [];
  currentUser: number = 0;
  rownum: number = 1;
  current: number = 0;
  
// substractNum:number=0;

  Submit: boolean = false;
  addTaskRow() {
    this.addDataToAllarows();
    if((this.fetchedDetails.length>0)&&!((this.fetchedDetails[0].timesheetStatusname==='Submitted')||(this.fetchedDetails[0].timesheetStatusname==='Approved')))
      {
    this.saveAndSubmit = true;
      }
 
    
    if((this.fetchedDetails.length>0)&&((this.fetchedDetails[0].timesheetStatusname==='Submitted')||(this.fetchedDetails[0].timesheetStatusname==='Approved')))
    {
     
      Swal.fire({
        text: 'timesheetalready submitted',
        icon: 'warning',
        confirmButtonText: 'OK',
      });

    }
    else{
      
      this.rownum++;
    // this.substractNum=1;
        this.tasks.push({
          project: '',
          taskType: '',
          task: '',
          attendanceType: '',
          days: Array(7).fill(0),
        });
       // this.columnsum();
     
       
      }
   // 
    //this.tasks=[];
   
      
  
    this.getHolidayDetails(this.startDate);
  

  }


  valuee: number = 0;
  removeTask(index: number): void {
   
    if (this.rownum > 0) {
      this.tasks.splice(index, 1);
      //alert(this.rownum)
      this.rownum--;
      //alert(this.rownum)
    }
    

    for (let a = 0; a < 6; a++) {
      this.totalvalue[a] =
        Number(this.totalvalue[a]) -
        Number(
          (document.getElementById('data_' + a + index) as HTMLInputElement)
            .value
        );
    }
  }

  ngOnInit(): void {
    const userStr = localStorage.getItem('user');
    this.Submit = false;
    if (userStr !== null) {
      const userData = JSON.parse(userStr);
      const userEmpId = userData.userEmpId;
      this.everyRowRecord[(this.rownum, 0)] = userEmpId;
      this.currentUser = userEmpId;
    } else {
      console.error('User data not found in localStorage');
    }
    this.timesheetHomeService.getAccounts(this.currentUser).subscribe(
      (resp) => {
      //  alert(resp)

        this.accounts = resp as any[];
      },
      (error) => {
        console.error(error);
      }
    );
   
    this.onSelectAttendanceType();
    this.calculateCurrentWeek();
  }
 // accountSelected:boolean=false;
  OnSelectAccount(account: any) {
    
    this.selectedAccount = account.target.value;
    this.addbutton = true;
    this.saveAndSubmit = false;
    //this.accountSelected = true;
    this.everyRowRecord[(this.rownum, 21)] = Number(this.selectedAccount);

    this.timesheetHomeService
      .getproject(this.currentUser, this.selectedAccount)
      .subscribe(
        (resp) => {
         
          this.projects = resp as any[];
          
          console.log(this.projects);
        },
        (error) => {
          console.error(error);
        }
      );
  }

  ngAfterViewChecked() {
    //this.columnsumnew();
   // this.columnsum();
   this.sumofields()

  }
  onSelect(projects: any, i: number) {
   

    this.selectedProjectId = projects.target.value;
    this.everyRowRecord[(this.rownum, 1)] = Number(this.selectedProjectId);
    this.timesheetHomeService
      .getProjectTaskType(this.currentUser, this.selectedProjectId)
      .subscribe((res) => {
        this.projectTaskType[i] = res as any[];
      });
  }
  selectedTasks: number[] = [];
  selectedTasksFromFetch: number[] = [];
  nextDropdownOptions: any[] = [];
  onSelectTaskType(projectTaskType: any, i: number) {
    this.selectedProjecttaskId = projectTaskType.target.value;
    this.everyRowRecord[(this.rownum, 2)] = Number(this.selectedProjecttaskId);
    this.timesheetHomeService
      .getProjectTask(this.selectedProjecttaskId)
      .subscribe((restask) => {
        console.log(restask);

        const filteredTasks = restask.filter(
          (task) =>
            !this.selectedTasks.some(
              (selectedTask) => Number(selectedTask) === task.taskId
            )
        ) as any[];

        this.projectTask[i] = filteredTasks;
      });
  }

  onSelectingTask(projectTask: any, i: number) {
    this.selectedTaskId = projectTask.target.value;

    this.everyRowRecord[(this.rownum, 3)] = Number(this.selectedTaskId);

    this.selectedTasks[this.fetchedDetails.length + i] = this.selectedTaskId;
  }
  onSelectAttendanceType() {
    this.timesheetHomeService.getBillingType().subscribe((bill) => {
      this.attendanceTypeArr = bill as any[];
    });
  }

  onselectAttendanceType(attendanceType: any, i: number) {
    const attendanceId = attendanceType.target.value;
    this.selectedAttendanceType=attendanceType.target.value;
    this.everyRowRecord[(this.rownum, 4)] = Number(attendanceId);
  }
  showButtons: boolean = true;
  calculateWeek(offset: number = 0) {
    this.saveAndSubmit = false;
    this.showButtons = true;
    this.addbutton = true;
    const today = new Date();
    const currentDay = today.getDay();
    const daysUntilMonday = currentDay === 0 ? 6 : currentDay - 1;
    const firstDayOfWeek = today.getDate() - daysUntilMonday + offset * 7;

    const startDate = new Date(today);
    startDate.setDate(firstDayOfWeek);

    this.currentWeek = [];

    let endDate = new Date(startDate);
    for (let i = 0; i <= 6; i++) {
      const currentDate = new Date(startDate);

      if (i == 0) {
        this.startDate = currentDate.setDate(startDate.getDate() + i);
        this.startDate = this.datePipe.transform(this.startDate, 'dd-MMM-yyyy');
      }
      if (i == 6) {
        endDate.setDate(startDate.getDate() + i);
        this.lastDate = endDate;
        this.lastDate = this.datePipe.transform(this.lastDate, 'dd-MMM-yyyy');
      }
      currentDate.setDate(startDate.getDate() + i);
      const formattedDate = this.datePipe.transform(currentDate, 'dd-MMM EEE');
      const formattedDate1 = this.datePipe.transform(
        currentDate,
        'dd-MMM-YYYY'
      );
      this.currentWeek.push({
        startDate: formattedDate,
        endDate: formattedDate,
      });
      // this.fetchWeekDayData(
      //   this.currentUser,
      //   this.selectedAccount,
      //   this.startDate,
      //   this.lastDate
      // );
      this.loadTimesheetData()

      this.everyRowRecord[(this.rownum, 5 + i)] = formattedDate1;
    }
    this.getHolidayDetails(this.startDate);
  }

  calculateCurrentWeek() {
    this.current = 0;
    this.calculateWeek(this.current);
    this.getHolidayDetails(this.startDate);
  }
  getWeekNumber(date: Date): number {
    const d = new Date(date);
    d.setHours(0, 0, 0, 0);
    d.setDate(d.getDate() + 4 - (d.getDay() || 7));
    const yearStart = new Date(d.getFullYear(), 0, 1);
    const weekNumber = Math.floor(
      ((d.getTime() - yearStart.getTime()) / 86400000 + 1) / 7
    );

    return weekNumber;
  }
  selectAccount: boolean = false;
  addbutton: boolean = false;
  showNextWeek() {
    this.calculateWeek(++this.current);

    this.tasks = [];

    this.loadTimesheetData()
    if (this.current != 0) {
      this.showButtons = false;
      this.addbutton = false;
      this.selectAccount = true;
      this.saveAndSubmit = false;
      this.Submit = false;
    }

    this.getHolidayDetails(this.startDate);
  }
  showPreviousWeek() {
  
    this.calculateWeek(--this.current);
    this.tasks = [];
   
    this.lastDate
    this.loadTimesheetData()
    if (this.current != 0) {
      this.showButtons = false;
      this.addbutton = false;
      this.selectAccount = true;
      this.Submit=false;
      this.saveAndSubmit = false;
    }
    this.getHolidayDetails(this.startDate);
  }

  totalvalue: number[] = [0, 0, 0, 0, 0, 0, 0];
  newrowTotal: number[] = [];
  // columnsumnew() {
  
  //   this.totalvalue = [0, 0, 0, 0, 0, 0, 0];
  //   for (let columnCount = 4; columnCount < 11; columnCount++) {
  //     let sum: number = 0;

  //     for (let rowCount = 0; rowCount < this.limitRow; rowCount++) {
  //       const inputElement = document
  //         .getElementById('data_' + rowCount + columnCount)
  //         ?.querySelector('input');
  //       if (inputElement instanceof HTMLInputElement) {
  //         const inputValue = inputElement.value;

  //         sum += Number(inputValue);
  //       }
  //     }

  //     this.totalvalue[columnCount - 4] = sum;
  //   }

  //   this.rowsumnew();

  //   return this.totalvalue;
  // }
  rowsumnew() {
    for (let rowCount = 0; rowCount < this.limitRow; rowCount++) {
      let sum: number = 0;
      for (let columnCount = 4; columnCount < 11; columnCount++) {
        const inputElement = document
          .getElementById('data_' + rowCount + columnCount)
          ?.querySelector('input');
        if (inputElement instanceof HTMLInputElement) {
          const inputValue = inputElement.value;
          sum += Number(inputValue);
        }
      }

      this.newrowTotal[rowCount] = sum;
    }
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
      this.everyRowRecord[(this.rownum, 12 + columnCount)] = Number(inputValue);
    }
    (document.getElementById('input_' + count + 7) as HTMLInputElement).value =
      String(sum);
  }

  getComment(comments: any) {
    const comment = comments.target.value;

    this.everyRowRecord[(this.rownum, 19)] = comment;
  }

  timesheetStatus: any[] = [];

  allRows: TimesheetWeekDayBean[] = [];
  saveAndSubmit: boolean = false;
  saveWeekTableData() {
    
    
    if(!this.selectedAccount||!this.selectedProjectId||!this.selectedAttendanceType||!this.selectedTaskId)
    {
      Swal.fire({
        title: 'Enter required fields to save timesheet',
        
        confirmButtonText: 'OK',
      });
      
    }
    else{
      this.addDataToAllarows();
      console.log(this.saveAndEditRecords);
      
      this.saveAndEditRecords.timesheetWeekDayDetailDto = this.allRows;
      this.saveAndEditRecords.weekAndDayDto = this.editedArray;
      if (!this.getworkingHours()) {
        Swal.fire({
          title: 'Are you sure?',
          text: 'working hour is less than 8 do you want to save',
          icon: 'warning',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
        }).then((result) => {
          if (result.isConfirmed) {
            this.timesheetHomeService
              .sendDataToBackend1(this.saveAndEditRecords, this.startDate)
              .subscribe(
                (response) => {
                  console.log('Backend response:', response);
                  Swal.fire({
                    title: 'Timesheet saved',
                    
                    confirmButtonText: 'OK',
                  });
                  this.saveAndSubmit = true;
                  this.tasks=[];
                 // this.editedArray = [];
                  this.loadTimesheetData()
                },
                (error) => {
                  console.error('Error sending data to backend:', error);
                }
              );
          } else {
            if (result.isDismissed) {
              console.log('else not saved');
            }
            console.log('save');
          }
        });
      }
    }
   
  }

  addDataToAllarows() {
    let index: number = 0;
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
    timesheetWeekDayBean.employeeId = this.everyRowRecord[index++];
    timesheetWeekDayBean.accountProjectId = this.everyRowRecord[index++];

    timesheetWeekDayBean.taskTypeId = this.everyRowRecord[index++];
    timesheetWeekDayBean.taskId = this.everyRowRecord[index++];
    timesheetWeekDayBean.attendanceType = this.everyRowRecord[index++];
    timesheetWeekDayBean.dateMon = this.everyRowRecord[index++];

    timesheetWeekDayBean.dateTue = this.everyRowRecord[index++];

    timesheetWeekDayBean.dateWed = this.everyRowRecord[index++];

    timesheetWeekDayBean.dateThu = this.everyRowRecord[index++];

    timesheetWeekDayBean.dateFri = this.everyRowRecord[index++];

    timesheetWeekDayBean.dateSat = this.everyRowRecord[index++];

    timesheetWeekDayBean.dateSun = this.everyRowRecord[index++];

    timesheetWeekDayBean.hoursMon = this.everyRowRecord[index++];
    timesheetWeekDayBean.hoursTue = this.everyRowRecord[index++];
    timesheetWeekDayBean.hoursWed = this.everyRowRecord[index++];
    timesheetWeekDayBean.hoursThu = this.everyRowRecord[index++];
    timesheetWeekDayBean.hoursFri = this.everyRowRecord[index++];
    timesheetWeekDayBean.hoursSat = this.everyRowRecord[index++];
    timesheetWeekDayBean.hoursSun = this.everyRowRecord[index++];
    timesheetWeekDayBean.comments = this.everyRowRecord[index++];
    timesheetWeekDayBean.timesheetStatus = this.everyRowRecord[index++];
    timesheetWeekDayBean.accountId = this.everyRowRecord[index++];

    if (timesheetWeekDayBean.accountProjectId != undefined) {
    //  this.allRows[this.rownum - 1] = timesheetWeekDayBean;
    this.allRows[this.rownum-1 ] = timesheetWeekDayBean;
    }
  }

  limitRow: number = 0;
  fetchedDetails: WeekAndDayDto[] = [];
  deetails: WeekAndDayDto[] = [];

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);
    const seconds = ('0' + date.getSeconds()).slice(-2);

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  }

  formattedDate: string = '';
  fetchWeekDayData(
    employeeId: number,
    accountId: number,
    startDate: string,
    endDate: string
  ): void {
    this.saveAndSubmit = false;
    this.Submit = true;
    
    

    this.timesheetHomeService
      .getWeekDayDetails(accountId, employeeId, startDate, endDate)
      .subscribe((fetched) => {
        
        this.fetchedDetails = fetched as WeekAndDayDto[];
        this.limitRow = fetched.length;
     
        if(this.fetchedDetails.length==0)
          {
            this.rownum=1;
            this.saveAndSubmit=true;
        
           
          }
          else{
            this.rownum=0;
            this.saveAndSubmit=false;
            this.tasks=[];
           // this.substractNum=-1;
          
          }
          
        this.getHolidayDetails(this.startDate);
        this.disableInputField();
       // this.countofrow = 1;
        for (let i = 0; i < this.fetchedDetails.length; i++) {
          this.selectedTasks[i] = this.fetchedDetails[i].taskId;
        }
        if (this.fetchedDetails[0].timesheetStatusname === 'Submitted') {
          this.saveAndSubmit = false;
        }
     
      });
  }

  loadTimesheetData(): void {
    const accountId = this.selectedAccount || this.defaultAccountId;
    this.fetchWeekDayData(
      this.currentUser,
      accountId,
      this.startDate,
      this.lastDate
    );
  }

  deleteselected(index: number) {
  
    if((this.fetchedDetails.length>0)&&((this.fetchedDetails[0].timesheetStatusname==='Submitted')||(this.fetchedDetails[0].timesheetStatusname==='Approved')))
      {
       
        Swal.fire({
          text: 'timesheetalready submitted you cant delete',
          icon: 'warning',
          confirmButtonText: 'OK',
        });
  
      }
    else
    
  { 
    Swal.fire({
    title: 'Are you sure?',
    text: 'This action cannot be undone!',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Yes, delete it!',
    cancelButtonText: 'No, keep it',
  }).then((result) => {
    if (result.isConfirmed) {
      const selectedRowData = this.fetchedDetails[index];
      const weekAndDayDto: WeekAndDayDto = {
        timesheetWeekId: selectedRowData.timesheetWeekId,
        accountId: selectedRowData.accountId,

        employeeId: selectedRowData.employeeId,
        accountProjectId: selectedRowData.accountProjectId,
        projectName: selectedRowData.projectName,
        taskTypeId: selectedRowData.taskTypeId,
        taskTypeName: selectedRowData.taskTypeName,
        taskId: selectedRowData.taskId,
        taskName: selectedRowData.taskName,
        attendanceType: selectedRowData.attendanceType,
        attendanceTypeName: selectedRowData.attendanceTypeName,
        weekStartDate: selectedRowData.weekStartDate,
        dateMon: selectedRowData.dateMon,
        dateTue: selectedRowData.dateTue,
        dateWed: selectedRowData.dateWed,
        dateThu: selectedRowData.dateThu,
        dateFri: selectedRowData.dateFri,
        dateSat: selectedRowData.dateSat,
        dateSun: selectedRowData.dateSun,
        hoursMon: selectedRowData.hoursMon,
        hoursTue: selectedRowData.hoursTue,
        hoursWed: selectedRowData.hoursWed,
        hoursThu: selectedRowData.hoursThu,
        hoursFri: selectedRowData.hoursFri,
        hoursSat: selectedRowData.hoursSat,
        hoursSun: selectedRowData.hoursSun,
        comments: selectedRowData.comments,
        timesheetStatus: 0,
        timesheetStatusname: '',
      };

      this.timesheetHomeService.deleteRecord(weekAndDayDto).subscribe(
        (response) => {
         
          Swal.fire('Deleted!', 'Your record has been deleted.', 'success');
          this.loadTimesheetData();
          
          
         
        },
        (error) => {
          Swal.fire('Failed to Delete', 'There was an error while deleting your record.', 'error');
          
        }
      );
    } 
  });}
   
    this.loadTimesheetData();
  }
  convertedDate: string = '';
  onSubmit() {
    const currentWeekStartDate = this.startDate;

    const datePipe = new DatePipe('en-US');

    const formattedDatee: string = currentWeekStartDate
      ? datePipe.transform(currentWeekStartDate, 'yyyy-MM-dd HH:mm:ss') || ''
      : '';

    if (
      this.fetchedDetails[0].timesheetStatusname === 'Pending' ||
      this.fetchedDetails[0].timesheetStatusname === 'Rejected'
    ) {
      if (!this.getWorkingHoursperWeek()) {
        Swal.fire({
          title: 'Are you sure?',
          text: 'working hour is more than 40 hours do you want to submit',
          icon: 'warning',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
        }).then((result) => {
          if (result.isConfirmed) {
            this.onSubmit1(
              this.currentUser,
              this.selectedAccount,
              formattedDatee
            );
          }
        });
      }
    } else {
      Swal.fire({
        text: 'timesheetalready submitted',
        icon: 'warning',
        confirmButtonText: 'OK',
      });
    }
  }

  onSubmit1(employeeId: number, accountId: number, weekStartDate: string) {
    this.timesheetHomeService
      .submitData(employeeId, accountId, weekStartDate)
      .subscribe(
        (response) => {
          this.saveAndSubmit = false;
       
          
          Swal.fire({
            title: 'Timesheet Submitted Successfully',
            
            confirmButtonText: 'OK',
          });
          this.loadTimesheetData();
        },
        (error) => {
          Swal.fire({
            title: 'Timesheet Submitted Successfully',
            
            confirmButtonText: 'OK',
          });
       
        }
      );
      this.loadTimesheetData();
  }

  editedArray: WeekAndDayDto[] = [];

  editedRow: WeekAndDayDto = new WeekAndDayDto(
    0,
    0,
    0,
    0,
    '',
    0,
    '',
    0,
    '',
    0,
    '',
    new Date(),
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
    ''
  );

  editMode: boolean = false;
  existingRows: number[] = [];
  flag: boolean = false;
  onEditMode(index: number, data: any) {
    this.saveAndSubmit = true;
    const indexExists = this.existingRows
      .map((row) => Number(row))
      .includes(index);
    if (!indexExists) {
      this.existingRows.push(index);
      if (!this.editMode) {
        this.editedArray.push(data);
      }
    } else {
    }
  }

  editedHoursMonArray: { [key: number]: number } = {};

  editedHoursTueArray: { [key: number]: number } = {};

  editedHoursWedArray: { [key: number]: number } = {};

  editedHoursThuArray: { [key: number]: number } = {};

  editedHoursFriArray: { [key: number]: number } = {};

  monvar: boolean = false;
  tuevar: boolean = false;
  wedvar: boolean = false;
  thuvar: boolean = false;
  frivar: boolean = false;

  geteditedHoursMon(event: any, j: number) {
    this.monvar = true;
    const newHoursMon = event.target.value;

    if (newHoursMon !== this.fetchedDetails[j].hoursMon) {
      this.editedHoursMonArray[j] = newHoursMon;
    } else {
      this.editedHoursMonArray[j] = this.fetchedDetails[j].hoursMon;
    }
    this.fetchedDetails[j].hoursMon = Number(newHoursMon);

    this.sumofields();

    this.editedHoursMonArray[j];
  }

  geteditedHoursTue(event: any, j: number) {
    this.tuevar = true;
    const newHoursTue = event.target.value;

    if (newHoursTue !== this.fetchedDetails[j].hoursTue) {
      this.editedHoursTueArray[j] = newHoursTue;
    } else {
      this.editedHoursTueArray[j] = this.fetchedDetails[j].hoursTue;
    }

    this.fetchedDetails[j].hoursTue = Number(newHoursTue);

    this.sumofields();

    this.editedHoursTueArray[j];
  }

  geteditedHoursWed(event: any, j: number) {
    this.wedvar = true;
    const newHoursWed = event.target.value;

    if (newHoursWed !== this.fetchedDetails[j].hoursWed) {
      this.editedHoursWedArray[j] = newHoursWed;
    } else {
      this.editedHoursWedArray[j] = this.fetchedDetails[j].hoursWed;
    }
    this.fetchedDetails[j].hoursWed = Number(newHoursWed);

    this.sumofields();

    this.editedHoursWedArray[j];
  }

  geteditedHoursThu(event: any, j: number) {
    this.thuvar = true;
    const newHoursThu = event.target.value;

    if (newHoursThu !== this.fetchedDetails[j].hoursThu) {
      this.editedHoursThuArray[j] = newHoursThu;
    } else {
      this.editedHoursThuArray[j] = this.fetchedDetails[j].hoursThu;
    }
    this.fetchedDetails[j].hoursThu = Number(newHoursThu);
    this.sumofields();
    this.editedHoursThuArray[j];
  }

  geteditedHoursFri(event: any, j: number) {
    this.frivar = true;
    const newHoursFri = event.target.value;

    if (newHoursFri !== this.fetchedDetails[j].hoursFri) {
      this.editedHoursFriArray[j] = newHoursFri;
    } else {
      this.editedHoursFriArray[j] = this.fetchedDetails[j].hoursFri;
    }
    this.fetchedDetails[j].hoursFri = Number(newHoursFri);
    this.sumofields();
    this.editedHoursFriArray[j];
  }

  addeditedrow() {}

  disableInputField() {
    setTimeout(() => {
      this.disableHolidayInputField();
    }, 100);
  }
  disableHolidayInputField() {
    for (let i = 0; i < this.holidays.length; i++) {
      const index = this.holidays[i];

      const inputelementId = 'input_' + (this.rownum - 1) + index;
      const inputElement = document.getElementById(
        inputelementId
      ) as HTMLInputElement;

      if (inputElement) {
        inputElement.style.backgroundColor = 'rgb(229, 233, 234)';
      }
      var tempRownum = this.rownum;

      for (let i = 0; i < this.fetchedDetails.length; i++) {
        const num = Number(index + 4);
        const inputelementId1 = 'tddata_' + (tempRownum - 1) + num;
        tempRownum = tempRownum + 1;

        const inputElement1 = document.getElementById(
          inputelementId1
        ) as HTMLInputElement;
        if (inputElement1) {
          inputElement1.disabled = true;
        }
      }
    }
  }

  public hoursPerDay: any;
  public hoursPerWeek: any;

  getworkingHours() {
    var flag = true;
    const minHoursDay = 'MinHoursDay';

    this.timesheetHomeService.getDayHours(minHoursDay).subscribe((data) => {
      const details = data;

      const hours = details[0].referenceDetailValue;
      this.hoursPerDay = Number(hours);

      this.hoursPerWeek = this.hoursPerDay * 5;
    });

    var result = this.totalvalue.reduce((sum, num) => sum + num, 0);
    for (let i = 0; i < this.totalvalue.length; i++) {
      if (this.totalvalue[i] != this.hoursPerDay) {
        flag = false;

        break;
      }
    }

    return flag;
  }
  getWorkingHoursperWeek() {
    var flag = true;
    const minHoursDay = 'MinHoursDay';

    this.timesheetHomeService.getDayHours(minHoursDay).subscribe((data) => {
      const details = data;

      const hours = details[0].referenceDetailValue;
      this.hoursPerDay = Number(hours);

      this.hoursPerWeek = this.hoursPerDay * 5;
      //alert(this.hoursPerWeek);
    });
    var result = this.totalvalue.reduce((sum, num) => sum + num, 0);

    for (let i = 0; i < this.totalvalue.length; i++) {
      if (result != this.hoursPerWeek) {
        flag = false;

        break;
      }
    }

    return flag;
  }

  getHolidayDetails(startdate: string) {
    this.timesheetHomeService.getHolidays(startdate).subscribe((data) => {
      if (Array.isArray(data) && data.length > 0) {
        data.forEach((item) => this.holidays.push(item));

        for (let i = 0; i < this.holidays.length; i++) {
          const index = this.holidays[i];

          const inputelementId = 'input_0' + index;
          const inputElement = document.getElementById(
            inputelementId
          ) as HTMLInputElement;

          if (inputElement) {
            inputElement.style.backgroundColor = 'rgb(229, 233, 234)';
          }
        }
      } else {
        console.error('Data received is not an array:', data);
        this.holidays = [];
      }
    });
  }


  sumofields()
  {
    this.totalvalue = [0, 0, 0, 0, 0, 0, 0];
    for (let columnCount = 4; columnCount < 11; columnCount++) {
      let sum: number = 0;

      for (let rowCount = 0; rowCount < this.limitRow; rowCount++) {
        const inputElement = document
          .getElementById('data_' + rowCount + columnCount)
          ?.querySelector('input');
        if (inputElement instanceof HTMLInputElement) {
          const inputValue = inputElement.value;

          sum += Number(inputValue);
        }
      }

      this.totalvalue[columnCount - 4] = sum;
    }

    this.rowsumnew();
    for (let columnCount = 0; columnCount < 7; columnCount++) {
      let sum: number = 0;

      for (let rowCount = 0; rowCount < this.rownum; rowCount++) {
		console.log( "ROWNUM" , this.rownum);
  
        const inputValue = (
          document.getElementById(
            'input_' + rowCount + columnCount
          ) as HTMLInputElement
        ).value;
        sum += Number(inputValue);
        console.log('input_' + rowCount + columnCount ,"--->" ,Number(inputValue));
        console.log(rowCount);
        
        this.rowsum(rowCount);
      }

      this.totalvalue[columnCount] += Number(sum);
    }

    return this.totalvalue;


  }

  isSubmitted(): boolean {
    return this.fetchedDetails[0].timesheetStatusname === 'Submitted'|| this.fetchedDetails[0].timesheetStatusname === 'Approved';
  }
}
