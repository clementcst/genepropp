import { Component, Inject  } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TreeService } from '../../../services/tree/tree.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-special-success-popup',
  templateUrl: './special-success-popup.component.html',
  styleUrls: ['./special-success-popup.component.css']
})
export class SpecialSuccessPopupComponent {

  constructor(
    public dialogRef: MatDialogRef<SpecialSuccessPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private treeService : TreeService,
    private router: Router
    ) {
      dialogRef.disableClose = true;
      this.treeService = treeService;
    }

  valider() {
    console.log(this.data.data.value.requestYes)
    this.treeService.wantToMergeNode(this.data.data.value.requestYes)
    this.dialogRef.close({action: 'Submit'});
  }

  pasValider() {
    this.dialogRef.close({action: 'Submit'});
  }

}
