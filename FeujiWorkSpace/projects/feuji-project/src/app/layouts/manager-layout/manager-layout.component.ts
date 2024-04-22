import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-manager-layout',
  templateUrl: './manager-layout.component.html',
  styleUrl: './manager-layout.component.css'
})
export class ManagerLayoutComponent {

  ngOnInit() {
    console.log(this.managerMenuOptions);
    throw new Error('Method not implemented.');
  }
  managerMenuOptions = [
    {
      mainOption: 'Dashboard',icon: 'fa-solid fa-tachometer-alt',
      routerLink: '/manager/manager-home'
    },
    { mainOption: 'User', icon: 'bi bi-person',subOptions: [
      { label: 'View Profile', link: '/manager/manager-profile' },
    ] },
    { mainOption: 'Timesheet',icon: 'bi bi-calendar-check', subOptions: [
      { label: 'New Timesheet', link: '/manager/timesheet-homemanager' },
      { label: 'Timesheet History', link: '/manager/timsheet-historymanager' },
      { label: 'Timesheet Approval', link: '/manager/timesheet-approval' },
      { link: '/manager/DailyStatusComponent' },

    ] },
    {
      mainOption: 'Holiday', icon: 'fas fa-gifts', subOptions: [
        { label: 'All Holiday List', link: '/manager/holiday-list' },
      ]
    },
    { mainOption: 'Skills', icon: 'bi bi-journal-text',subOptions: [
      { label: 'Skill Gap', link: '/manager/skill-gap' },
      { label: 'Training Recommendations', link: '/manager/training-recommendation' }
    ] },
    
  ];


}
