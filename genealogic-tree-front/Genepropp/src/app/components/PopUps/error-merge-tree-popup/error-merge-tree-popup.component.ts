import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-error-merge-tree-popup',
  templateUrl: './error-merge-tree-popup.component.html',
  styleUrls: ['./error-merge-tree-popup.component.css']
})
export class ErrorMergeTreePopupComponent {

  constructor(
    public dialogRef: MatDialogRef<ErrorMergeTreePopupComponent>,
    @Inject(MAT_DIALOG_DATA) public errorMessage: any
    ) {}

  valider() {
    console.log(this.errorMessage)
    this.dialogRef.close({action: 'Submit'});
  }

}
