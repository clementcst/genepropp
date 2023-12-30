import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user/user.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-profil-rigth',
  templateUrl: './profil-rigth.component.html',
  styleUrls: ['./profil-rigth.component.css']
})
export class ProfilRigthComponent implements OnInit {
  user: any = {};
  userOnInit: any = {};
  inputs1: any[] = [];
  inputs2: any[] = [];
  showSuccessMessage: boolean = false;
  successMessage: string = '';
  showFailedMessage: boolean = false;
  failedMessage: string = '';
  loadingUser: boolean = false;
  loadingpage2: boolean = false;

  constructor(private userService : UserService, private cookieService: CookieService) { 
    this.userService = userService;
    this.cookieService = cookieService;
  }

  ngOnInit(): void {
    this.loadingpage2 = true;
    this.showUserProfil()
  }

  private showUserProfil() {
    this.userService.getUser(this.cookieService.get('userId')).subscribe((data) => {
      this.user = data.value;
      this.loadingpage2 = false;
      this.userOnInit = { ...this.user };
      this.inputs1 = [
        { label: "firstname", type: "text", fieldname: "Firstname", value: this.user.firstName, disabled: true },
        { label: "lastname", type: "text", fieldname: "Lastname", value: this.user.lastName, disabled: true },
        { label: "secu", type: "text", fieldname: "Secu number", value: this.user.noSecu },
        { label: "phone_number", type: "tel", fieldname: "Phone number", value: this.user.noPhone, pattern: "[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}" },
      ];

      this.inputs2 = [
        { label: "nationality", type: "text", fieldname: "Nationality", value: this.user.nationality },
        { label: "email", type: "email", fieldname: "Email", value: this.user.email },
        { label: "address", type: "text", fieldname: "Address", value: this.user.adress }
      ];
    });
  }

  updateInputsData(updatedValue: { label: string, value: any }) {
    const index1 = this.inputs1.findIndex(input => input.label === updatedValue.label);
    const index2 = this.inputs2.findIndex(input => input.label === updatedValue.label);
  
    if (index1 !== -1) {
      this.inputs1[index1].value = updatedValue.value;
    } else if (index2 !== -1) {
      this.inputs2[index2].value = updatedValue.value;
    }
  }

  onSubmitModification() {
    this.loadingUser = true;
    const birthday = (document.getElementById('birthdayInput') as HTMLInputElement)?.value;
    const sexe = (document.querySelector('input[name="sexeInput"]:checked') as HTMLInputElement)?.value;
    const inputsData: any = {};
    if (this.inputs1.find(input => input.label === 'secu')?.value != this.userOnInit.noSecu) {
      inputsData.noSecu = this.inputs1.find(input => input.label === 'secu')?.value;
    }
    if (this.inputs1.find(input => input.label === 'phone_number')?.value != this.userOnInit.noPhone) {
      inputsData.noPhone = this.inputs1.find(input => input.label === 'phone_number')?.value;
    }
    if (birthday != this.userOnInit.dateOfBirth) {
      inputsData.dateOfBirth = birthday;
    }
    if (sexe != this.userOnInit.gender) {
      inputsData.gender = sexe;
    }
    if (this.inputs2.find(input => input.label === 'nationality')?.value != this.userOnInit.nationality) {
      inputsData.nationality = this.inputs2.find(input => input.label === 'nationality')?.value;
    }
    if (this.inputs2.find(input => input.label === 'email')?.value != this.userOnInit.email) {
      inputsData.email = this.inputs2.find(input => input.label === 'email')?.value;
    }
    if (this.inputs2.find(input => input.label === 'address')?.value != this.userOnInit.adress) {
      inputsData.adress = this.inputs2.find(input => input.label === 'address')?.value;
    }
    this.userService.updateUser(this.user.id, inputsData).subscribe(response => {
      if (response.success) {
        this.loadingUser = false;
        this.successMessage = response.message || 'Modification successful.';
        this.showSuccessMessage = true;
        setTimeout(() => {
          this.showSuccessMessage = false;
        }, 3000);

        this.showUserProfil();
      }
      else {
        this.loadingUser = false;
        this.failedMessage = response.message || 'Modification failed.';
        this.showFailedMessage = true;
        setTimeout(() => {
          this.showFailedMessage = false;
        }, 3000);
      }
    });
  }
}
