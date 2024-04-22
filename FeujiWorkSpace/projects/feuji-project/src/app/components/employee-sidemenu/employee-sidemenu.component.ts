import { Component } from '@angular/core';

@Component({
  selector: 'app-employee-sidemenu',
  templateUrl: './employee-sidemenu.component.html',
  styleUrl: './employee-sidemenu.component.css'
})
export class EmployeeSidemenuComponent {


  toggleSubmenu(submenuClass: string) {
    const submenu = document.querySelector(`.${submenuClass}`);
    if (submenu) {
      submenu.classList.toggle('show');
    }
  }
}

