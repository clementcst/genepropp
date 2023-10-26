import { Component } from '@angular/core';

@Component({
  selector: 'app-profil-left',
  templateUrl: './profil-left.component.html',
  styleUrls: ['./profil-left.component.css']
})
export class ProfilLeftComponent {
  boxs = [
    { title: "Month views", value: "45" },
    { title: "Annual views", value: "132" },
    { title: "Tree length", value: "4" }
  ];
}
