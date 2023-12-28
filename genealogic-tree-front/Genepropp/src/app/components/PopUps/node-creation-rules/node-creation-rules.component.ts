import { Component, Inject  } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-node-creation-rules',
  templateUrl: './node-creation-rules.component.html',
  styleUrls: ['./node-creation-rules.component.css']
})
export class NodeCreationRulesComponent {

  constructor(
    public dialogRef: MatDialogRef<NodeCreationRulesComponent>,
    @Inject(MAT_DIALOG_DATA) public erroMessage: any
    ) {}

  valider() {
    console.log(this.erroMessage)
    this.dialogRef.close({action: 'Submit'});
  }

}
