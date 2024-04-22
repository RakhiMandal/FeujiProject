import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-employee-layout',
  templateUrl: './employee-layout.component.html',
  styleUrl: './employee-layout.component.css'
})
export class EmployeeLayoutComponent implements OnInit {
  ngOnInit() {
    console.log("Employee layout menu option :", this.employeeMenuOptions);
    // throw new Error('Method not implemented.');
  }
  employeeMenuOptions = [
    {
      mainOption: 'Dashboard',icon: 'fa-solid fa-tachometer-alt',
      routerLink: '/employee/employee-home'
    },
    {
      mainOption: 'User', icon: 'bi bi-person', subOptions: [
        { label: 'View Profile', link: '/employee/emp-profile' },
      ]
    },
    {
      mainOption: 'TimeSheet', icon: 'bi bi-calendar-check', subOptions: [
        { label: 'New Timesheet', link: '/employee/timesheet-home' },
        { label: 'Timesheet History', link: '/employee/timsheet-history' },
      ]
    },
    {
      mainOption: 'Holiday', icon: 'fas fa-gifts', subOptions: [
        { label: 'All Holiday List', link: '/employee/holiday-list' },
      ]
    },
    {
      mainOption: 'Skill Gap', icon: 'bi bi-journal-text', subOptions: [
        { label: 'Update Skills', link: '/employee/update-skills' },
        { label: 'Skill Gap', link: '/employee/employee-skillgap' },
        { label: 'Training Recommendations', link: '/employee/employee-trainingRecommendation' }

      ]
    },
  ];

}


