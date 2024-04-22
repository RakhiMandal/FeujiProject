import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Employee } from '../../../../models/employee.model';

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrl: './edit-employee.component.css'
})
export class EditEmployeeComponent {
  constructor(
    public dialogRef: MatDialogRef<EditEmployeeComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Employee
  ) { }

  onCancelClick(): void {
    this.dialogRef.close();
  }
}
