import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-profil-inputs',
  templateUrl: './profil-inputs.component.html',
  styleUrls: ['./profil-inputs.component.css']
})
export class ProfilInputsComponent {
  @Input() input:any;

  isCursorNotAllowed(): boolean {
    return this.input.disabled === true;
  }
}
