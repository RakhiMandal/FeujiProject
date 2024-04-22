import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(private router: Router) { }

  canActivate(): boolean {
    var role = localStorage.getItem('designation');
    if (role === 'Admin') {
      return true;
    }
    else {
      if (role == 'Manager') {
        this.router.navigate(['/manager/manager-home']);
      }
      else if (role !== 'Admin' && role !== 'Manager') {
        this.router.navigate(['/employee/employee-home']);
      }
      
      else {
        this.router.navigate(['']);
      }
      return false;
    }
  }
}
