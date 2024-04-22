import { Component, OnInit } from '@angular/core';
import { holidayRepo } from '../../../../models/holiday.repo';
import { HolidayService } from '../../../../models/holidayservice.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Holiday } from '../../../../models/holiday.model';
import Swal from 'sweetalert2';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-editholiday',
  templateUrl: './editholiday-component.component.html',
  styleUrl: './editholiday-component.component.css'
})
export class EditholidayComponent  implements OnInit{
  constructor(private data: HolidayService, private router: Router, private activatedRoute: ActivatedRoute) { }
  holiday: Holiday = new Holiday(0, new Date, '', '', '', '', false, '', '', new Date, '', new Date);
  holidayForm!: FormGroup
  ngOnInit(): void {


    console.log(this.holiday.holidayDate);

    console.log(this.holiday);
    this.holiday = history.state.holiday;
    console.log(this.holiday);

    // console.log(this.holiday.holidayDate)


  }

  onData(): void {

    this.data.updateHoliday(this.holiday).subscribe(
      () => {

        console.log(this.holiday)
        Swal.fire({
          icon: 'success',
          title: 'Updated  Successfully!',
          text: 'Holiday details are Updated ',
         
        });
        this.router.navigate(['/admin/holiday-list'])
      })
  }
  
}