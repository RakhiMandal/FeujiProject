import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../../models/user.service';
import Swal from 'sweetalert2';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html'
})
export class ForgotPasswordComponent {
  email: string = '';
  otp: string = '';
  newPassword: string = '';
  step: 'email' | 'otp' | 'update' = 'email';

  constructor(private userService: UserService, private router: Router) {}

  checkEmail(): void {
    this.userService.checkEmailExist(this.email).subscribe(
      exists => {
        if (exists) {
          this.userService.generateOTP(this.email).subscribe(() => {
            this.step = 'otp';
          });
        } else {
          alert('Email does not exist');
        }
      },
      error => {
        console.error('Error checking email:', error);
        alert('An error occurred while checking the email.');
      }
    );
  }

  verifyOTP(): void {
    this.userService.verifyOTP(this.email, this.otp).subscribe(
      success => {
        if (success) {
          this.step = 'update';
        } else {
          alert('Invalid OTP');
        }
      },
      error => {
        console.error('Error verifying OTP:', error);
        alert('An error occurred while verifying the OTP.');
      }
    );
  }

  updatePassword(): void {
    this.userService.updatePassword(this.email, this.newPassword).subscribe(
      () => {
        Swal.fire({
          title: 'Success!',
          text: 'Password updated successfully',
          icon: 'success',
          confirmButtonText: 'OK'
        }).then(() => {

          this.router.navigate(['/login']);
        });
      },
      error => {
        console.error('Error updating password:', error);
        Swal.fire({
          title: 'Error!',
          text: 'An error occurred while updating the password.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    );
  }
}