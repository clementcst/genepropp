import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-error-merge-tree-popup',
  templateUrl: './error-merge-tree-popup.component.html',
  styleUrls: ['./error-merge-tree-popup.component.css']
})
export class ErrorMergeTreePopupComponent {

  constructor(
    public dialogRef: MatDialogRef<ErrorMergeTreePopupComponent>,
    @Inject(MAT_DIALOG_DATA) public errorMessage: any,
    private router: Router
    ) {
      dialogRef.disableClose = true;
    }

  valider() {

    if (this.errorMessage.data == "This family tree is set to private. Click below to be redirected to the main menu.") {
      // Redirection vers le menu principal (/homeMenu)
      this.router.navigate(['homePage']);
      this.dialogRef.close({ action: 'Submit' });
    } else {
      //console.log("redirection 2 ....")
      this.dialogRef.close({ action: 'Submit' });
    }
    
  }

}
