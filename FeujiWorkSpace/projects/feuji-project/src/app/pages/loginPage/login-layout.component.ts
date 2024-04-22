import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../models/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../models/AuthService';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login-layout',
  templateUrl: './login-layout.component.html',
  styleUrl: './login-layout.component.css',
})
export class LoginLayoutComponent {

  formData: FormGroup;
  // showDashboard: boolean = false;
  userEmail: string = '';
  userPassword: string = '';
  empDataById: any;
  isLoggedIn: boolean = false;

  isedIn: boolean = false;
  title: String = "LOGIN";
  email: string = '';
  password: string = '';

  constructor(private userService: UserService, private router: Router,
    private fb: FormBuilder, private authService: AuthService) {

    this.formData = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]]
    });
  }

  login() {
    const email = this.email;
    const password = this.password;

    if (!email || !password) {
      console.error('Invalid email or password');
      return;
    }

    const data = { email, password };

    this.userService.login(email, password).subscribe((user: any) => {
      Swal.fire({
        title: 'Login',
        text: 'Logged in successfully',
        icon: 'success',
        confirmButtonText: 'Ok',
      });
      const token = user.accessToken;
      console.log(token);
      this.authService.setAccessToken(token)
      this.authService.setRole(user.userEntity.designation);
      this.authService.setUser(JSON.stringify(user.userEntity))

      this.userService.getEmployeeByid(user.userEntity.userEmpId).subscribe(
        (result: any) => {
          this.empDataById = result[0];
          localStorage.setItem('user', JSON.stringify(this.empDataById));
          this.isLoggedIn = true;
        },
        (error) => {
          console.error('Error fetching employee details', error);
        }
      );

      const designation = user.userEntity.designation;
      console.log('User designation:', designation);


      switch (designation) {
        case 'Admin':
          console.log("Navigated to admin page");
          this.router.navigate(['/admin/admin-home-page']);
          break;
        case 'Manager':
          console.log("Navigated to manager page");
          this.router.navigate(['/manager/manager-home']);
          break;
        case 'PMO':
          console.log("Navigated to pmo page");
          this.router.navigate(['/add-account']);
          break;
        default:
          console.log("Navigated to employee page");
          this.router.navigate(['/employee/employee-home']);
          break;


      }
    },
      (error) => {
        Swal.fire({
          title: 'Login',
          text: 'Invalid Credentials',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
        console.error('Error fetching employee details', error);
      });
  }

}



















// constructor(private userService: UserService, private router: Router, private fb: FormBuilder, private userRepo: UserRepo) {
//   this.formData = this.fb.group({
//     userEmail: ['', [Validators.required]],
//     userPassword: ['', [Validators.required]]
//   });
// }
