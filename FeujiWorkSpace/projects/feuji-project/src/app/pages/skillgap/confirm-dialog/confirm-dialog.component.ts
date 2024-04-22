import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Inject } from '@angular/core';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrl: './confirm-dialog.component.css'
})
export class ConfirmDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  /**
   *   Close the dialog with a false value
   */
  onNoClick() {
    this.dialogRef.close(false);
  }

  /**
   * Close the dialog with a true value
   */
  onYesClick() {
    this.dialogRef.close(true);
  }
}
