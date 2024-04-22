import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-navbar',
  templateUrl: './employee-navbar.component.html',
  styleUrl: './employee-navbar.component.css'
})
export class EmployeeNavbarComponent {
  constructor(private router: Router) {}
  logout() {
    this.router.navigate(['/login']);
  }

}
