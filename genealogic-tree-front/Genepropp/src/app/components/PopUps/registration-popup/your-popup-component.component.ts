import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-your-popup-component',
  templateUrl: './your-popup-component.component.html',
  styleUrls: ['./your-popup-component.component.css']
})
export class YourPopupComponentComponent {

  constructor(
    public dialogRef: MatDialogRef<YourPopupComponentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
    ) {}

  editer() {
    console.log('Editer');
    this.dialogRef.close({action: 'Edit'});
  }

  valider() {
    console.log('Valider');
    this.dialogRef.close({action: 'Submit'});
    
  }

}
