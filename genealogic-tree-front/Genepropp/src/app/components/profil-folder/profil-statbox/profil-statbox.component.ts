import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-profil-statbox',
  templateUrl: './profil-statbox.component.html',
  styleUrls: ['./profil-statbox.component.css']
})
export class ProfilStatboxComponent {
  @Input() box:any;
}
