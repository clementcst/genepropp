import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-profil-rigth',
  templateUrl: './profil-rigth.component.html',
  styleUrls: ['./profil-rigth.component.css']
})
export class ProfilRigthComponent implements OnInit {
  user: any = {};
  inputs1: any[] = [];
  inputs2: any[] = [];

  constructor(private userService : UserService ) { 
    this.userService = userService;
  }

  ngOnInit(): void {
    this.userService.getUser(2).subscribe((data) => {
      this.user = data;

      // Define inputs1 after receiving data
      this.inputs1 = [
        { label: "firstname", type: "text", fieldname: "Firstname", value: this.user.firstName, disabled: true },
        { label: "lastname", type: "text", fieldname: "Lastname", value: this.user.lastName, disabled: true },
        { label: "secu", type: "text", fieldname: "Secu number", value: this.user.noSecu },
        { label: "phone_number", type: "tel", fieldname: "Phone number", value: this.user.noPhone, pattern: "[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}" },
      ];

      this.inputs2 = [
        { label: "nationality", type: "text", fieldname: "Nationality of origin", value: this.user.nationality },
        { label: "email", type: "email", fieldname: "Email", value: this.user.email },
        { label: "address", type: "text", fieldname: "Address", value: this.user.adress }
      ];
    });
  }
}
