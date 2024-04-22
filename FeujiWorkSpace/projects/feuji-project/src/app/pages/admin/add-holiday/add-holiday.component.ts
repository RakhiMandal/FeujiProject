 import { Component, ViewChild } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Holiday } from '../../../../models/holiday.model';
import { holidayRepo } from '../../../../models/holiday.repo';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';
import { HolidayService } from '../../../../models/holidayservice.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-holiday',
  templateUrl: './add-holiday.component.html',
  styleUrl: './add-holiday.component.css'
})
export class AddHolidayComponent {
  constructor( private holidayService: HolidayService,private repo:holidayRepo,private datePipe: DatePipe,private router:Router) { }
  ngOnInit(): void {
   
  }
 
   holiday:Holiday=new Holiday(0,new Date,'','','','',false,'','',new Date,'',new Date);
   @ViewChild('holidayForm') holidayForm!: NgForm ;
  
  SendHoliday(holiday: Holiday) {
    this.holidayService.saveholiday(holiday).subscribe(
      (res) => {
        console.log("saved successfully");
        Swal.fire({
          icon: 'success',
          title: 'Saved Successfully!',
          text: 'Holiday details are saved ',
         
        });
        this.router.navigate(['/admin/holiday-list']);
      },
      (error: any) => {
        Swal.fire({
          icon: 'error',
          title: 'Holiday Exist',
          text: 'Holiday details already existed',
         
        });
        this.holidayForm.resetForm();
      }
    );
  }

  updateDayOfWeek() {
    const selectedDate = this.holiday.holidayDate;
    const dayOfWeek = this.datePipe.transform(selectedDate, 'EEEE') ?? ''; // Provide an empty string as default value if the result is null
    this.holiday.holidayDay = dayOfWeek;
  }
}
