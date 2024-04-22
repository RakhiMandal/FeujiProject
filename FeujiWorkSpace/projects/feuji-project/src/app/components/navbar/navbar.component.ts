import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  user: any;

  constructor(private router: Router, private titleService: Title) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.setTitleFromRoute();
      }
    });
  }



  checkUser() {
    const storedUser = localStorage.getItem('user');
    this.user = storedUser ? storedUser : undefined;

    if (this.user) {
      const designation = this.user.designation;
      console.log('User designation:', designation);

      switch (designation) {
        case 'Admin':
          console.log("Navigated to admin page");
          this.router.navigate(['/admin/add-employee']);
          break;
        case 'Manager':
          console.log("Navigated to manager page");
          this.router.navigate(['/manager/manager-profile']);
          break;
        case 'PMO':
          console.log("Navigated to pmo page");
          this.router.navigate(['/add-account']);
          break;
        default:
          console.log("Navigated to employee page");
          this.router.navigate(['/employee/timesheet-home']);
          break;
      }

    } else {
      this.router.navigate(['/login']);
    }
  }

  logout() {
    localStorage.removeItem('user');
    localStorage.clear();
    this.checkUser();
  }

  title: string = '';


  setTitleFromRoute() {
    // Get the current route URL
    const currentUrl = this.router.url;

    // Split the URL by '/' and get the last segment
    const segments = currentUrl.split('/');
    const lastSegment = segments[segments.length - 1];

    // Define logic to set the title based on the last segment of the route URL
    switch (lastSegment) {
      // Admin
      case 'admin-profile':
        this.title = 'Profile';
        break;
      case 'display-employee':
        this.title = 'Display Employee';
        break;
      case 'add-employee':
        this.title = 'Add New Employee';
        break;
      case'update-account/:id':
        this.title='Update Account';
        break;
      case 'projects':
        this.title = 'All Projects';
        break;
      case 'account-display':
        this.title = 'Account';
        break;
      case 'holiday-list':
        this.title = 'Holiday';
        break;
        // Employee
      case 'emp-profile':
        this.title = 'Profile';
        break;
      case 'timesheet-home':
        this.title = 'New Timesheet';
        break;
      case 'timsheet-history':
        this.title = 'Timesheet History';
        break;
      
        // Manager
      case 'manager-profile':
          this.title = 'Profile';
        break;
      case 'timesheet-homemanager':
          this.title = 'New Timesheet';
        break;
      case 'timsheet-historymanager':
          this.title = 'Timesheet History';
        break;
      case 'timesheet-approval':
          this.title = 'Timesheet For Approval';
        break;
      case 'dailyStatus':
          this.title = 'Daily status';
        break;
      case 'skill-gap':
          this.title = 'Skill Gap';
        break;
      case 'training-recommendation':
          this.title = 'Employees Recommended For Training';
        break;
      
        case 'update-skills':
          this.title = 'Update Skills';
        break;
      case 'employee-skillgap':
          this.title = 'My Skillgap';
        break;
      case 'employee-trainingRecommendation':
          this.title = 'Recommended Skills For Training';
        break;
      case 'add-skills':
        this.title=" Add Skills";
        break;
      default:
        this.title = 'Dashboard';
        break;
    }


    this.titleService.setTitle(this.title);
  }

}