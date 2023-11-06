import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-profil-rigth',
  templateUrl: './profil-rigth.component.html',
  styleUrls: ['./profil-rigth.component.css']
})
export class ProfilRigthComponent implements OnInit {
  users: any = {}; // Change this to an object instead of an array
  inputs1: any[] = []; 
  inputs2: any[] = []; 

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any>('http://localhost:8080/user/1').subscribe((data) => {
      this.users = data;
      console.log(data)

      // Define inputs1 after receiving data
      this.inputs1 = [
        { label: "firstname", type: "text", fieldname: "Firstname", value: this.users.firstName, disabled: true },
        { label: "lastname", type: "text", fieldname: "Lastname", value: this.users.lastName, disabled: true },
        { label: "secu", type: "text", fieldname: "Secu number", value: this.users.noSecu },
        { label: "phone_number", type: "tel", fieldname: "Phone number", value: this.users.noPhone, pattern: "[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}" },
      ];

      this.inputs2 = [
        { label: "nationality", type: "text", fieldname: "Nationality of origin", value: this.users.nationality },
        { label: "email", type: "email", fieldname: "Email", value: this.users.email },
        { label: "address", type: "text", fieldname: "Address", value: this.users.adress }
      ];
      // ... Define other inputs if needed
    });
  }
}
