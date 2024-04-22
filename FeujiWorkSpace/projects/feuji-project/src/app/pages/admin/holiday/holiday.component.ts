import { Component, OnInit } from '@angular/core';
import { HolidayService } from '../../../../models/holidayservice.service';
import { holidayRepo } from '../../../../models/holiday.repo';
import { Holiday } from '../../../../models/holiday.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';




@Component({
  selector: 'app-holiday',
  templateUrl: './holiday.component.html',
  styleUrl: './holiday.component.css'
})
export class HolidayComponent  implements OnInit
{
  public holidays: Holiday[] = [];
  public defaultYear: number;
  // Example years
  public selectedYear!: number;
  public holidayDetails: Holiday[] = [];
  public selectedmonth!: string;
  holidayYears: number[] = [];
  constructor(private repository: holidayRepo, private data: HolidayService, private router: Router) {
    const currentDate = new Date();
    this.defaultYear = currentDate.getFullYear(); // Set the default year to the current year
    this.selectedYear = this.defaultYear;
  }

   generateHolidayYears() {
    // Get the current year
    const currentYear = new Date().getFullYear();

    // Generate an array containing the previous two years and the current year
    const holidayYears = [currentYear - 2, currentYear - 1, currentYear];

   this.holidayYears=holidayYears;
  }



  ngOnInit(): void {
    // this.getholiday()
    this.generateHolidayYears()
    this.HolidaysByYear()
  }

  getholiday() {
    this.data.getholiday().subscribe(d => {
      this.holidays = d;
    })
  }


  navigateeditholiday(holiday: any) {
    //alert("navigate")
    // console.log(holidayDetails);
    this.router.navigate(["/admin/edit-holiday"], { state: { holiday } })

  }




  HolidaysByYear() {
    this.data.getHolidayByYear(this.selectedYear).subscribe(res => {


      this.holidayDetails = res;

      console.log(this.holidayDetails);

    })
  }
  removeTask(holidayId: Number) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this holiday!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result) => {
      if (result.isConfirmed) {
        console.log("component deleted", holidayId);
        this.data.deleteRow(holidayId).subscribe(res => {
          console.log(res);
          // Handle success message or any other action after deletion
          this.HolidaysByYear();
        });
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        // Handle cancel action
        // No deletion occurred
      }
    });
  }
}
