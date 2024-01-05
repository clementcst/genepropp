import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-show-private-code',
  templateUrl: './show-private-code.component.html',
  styleUrls: ['./show-private-code.component.css']
})
export class ShowPrivateCodeComponent {

  constructor(
    public dialogRef: MatDialogRef<ShowPrivateCodeComponent>,
    @Inject(MAT_DIALOG_DATA) public privatecode: any
    ) {}

  valider() {
    console.log(this.privatecode)
    this.dialogRef.close({action: 'Submit'});
  }

}