import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.css'
})
export class AdminLayoutComponent implements OnInit {

  ngOnInit(): void {
    console.log('AdminLayoutComponent initialized');
  }
  adminMenuOptions = [
    {
      mainOption: 'Dashboard',icon: 'fa-solid fa-tachometer-alt',
      routerLink: '/admin/admin-home-page'
    },
    { mainOption: 'User', icon: 'bi bi-person',subOptions: [
      { label: 'View Profile', link: '/admin/admin-profile'},
    ] },
    { mainOption: 'Employee',icon: 'bi bi-people', subOptions: [
      { label: 'View Employee', link: '/admin/display-employee' }
    ] },
    { mainOption: 'Projects',icon: 'bi bi-people', subOptions: [
      { label: 'View Projects', link: '/admin/projects' }
    ] },

    { mainOption: 'Account',icon: 'fa-solid fa-file-invoice-dollar', subOptions: [
      { label: ' View Account ', link: '/admin/account-display' },
    ] }
    ,
    { mainOption: 'Holiday',icon: 'fas fa-gifts', subOptions: [
      { label: 'Holiday List', link: '/admin/holiday-list' },
    ] },
    { mainOption: 'Skill Gap',icon: 'bi bi-journal-text', subOptions: [
      { label: 'Add Skills', link: '/admin/add-skills' },
    ] }
  ];


}
