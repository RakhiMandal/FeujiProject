import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class ManagerGuard implements CanActivate {
  constructor(private router: Router) { }

  canActivate(): boolean {
    var role = localStorage.getItem('designation');
    if (role === 'Manager') {
      return true;
    } else {
      if (role === 'Admin') {
        this.router.navigate(['/admin/admin-home-page']);
      }
      else if (role !== 'Admin' && role !== 'Manager') {
        this.router.navigate(['/employee/employee-home']);
      }

      else {
        this.router.navigate(['/login']);
      }
      return false;
    }
  }
}
