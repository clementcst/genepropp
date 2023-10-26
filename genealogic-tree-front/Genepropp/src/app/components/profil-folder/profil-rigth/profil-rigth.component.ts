import { Component } from '@angular/core';

@Component({
  selector: 'app-profil-rigth',
  templateUrl: './profil-rigth.component.html',
  styleUrls: ['./profil-rigth.component.css']
})
export class ProfilRigthComponent {
  inputs1 = [
    {label: "firstname", type: "text", fieldname: "Firstname", value: "Adam", disabled: true},
    {label: "lastname", type: "text", fieldname: "Lastname", value: "Bouhrara", disabled: true},
    {label: "secu", type: "text", fieldname: "Secu number", value: "212313213"},
    {label: "phone_number", type: "tel", fieldname: "Phone number", value: "0628079905", pattern: "[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}"},
  ];
  inputs2 = [
    {label: "nationality", type:"text", fieldname: "Nationality of origin", value: "France"},
    {label: "email", type:"email", fieldname: "Email", value: "bouhraraad@cy-tech.fr"},
    {label: "address", type:"text", fieldname: "Address", value: "5 rue gabriel péri, 95240, Cormeilles"}
  ];
}
