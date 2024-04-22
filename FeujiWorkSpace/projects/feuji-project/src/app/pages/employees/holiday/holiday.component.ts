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
  holidayYears: number[] = [2022, 2023, 2024]; // Example years
  public selectedYear!: number;
  public holidayDetails: Holiday[] = [];
  public selectedmonth!: string;

  constructor(private repository: holidayRepo, private data: HolidayService, private router: Router) {
    const currentDate = new Date();
    this.defaultYear = currentDate.getFullYear(); // Set the default year to the current year
    this.selectedYear = this.defaultYear;
  }




  ngOnInit(): void {
    // this.getholiday()
    this.HolidaysByYear()
  }

 


  


  HolidaysByYear() {
    this.data.getHolidayByYear(this.selectedYear).subscribe(res => {


      this.holidayDetails = res;

      console.log(this.holidayDetails);

    })
  }
 
}