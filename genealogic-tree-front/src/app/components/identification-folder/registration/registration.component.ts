import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { IdentificationService } from '../../../services/identificaton/identification.service';
import { MatDialog } from '@angular/material/dialog';
import { YourPopupComponentComponent } from '../../PopUps/registration-popup/your-popup-component.component'
import { ShowPrivateCodeComponent } from '../../PopUps/show-private-code-popup/show-private-code.component'

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  authenticationError: boolean = false;
  authenticationErrorMsg: string = "An error occurred, please contact support";
  step = 1;
  userResponse = 0;
  loading: boolean = false;
  data: any = {
    firstName: '',
    lastName: '',
    email: '',
    ssn: '',
    sexe: '',
    phn: '',
    birthDate: '',
    cityofbirth: '',
    countryofbirth: '',
    nationality: '',
    adress: '',
    postalCode: '',
    password: '',
    confirmPassword: '',
    message_error_step1: '',
  };
  errors: any = {
    isdetected: false,
    firstName: false,
    lastName: false,
    email: false,
    ssn: false,
    sexe: false,
    phn: false,
    birthDate: false,
    cityofbirth: false,
    countryofbirth: false,
    nationality: false,
    adress: false,
    postalCode: false,
    password: false,
    confirmPassword: false,
    step1error: false,
  };

  constructor(
    private identificationService: IdentificationService,
    private router: Router,
    private cookieService: CookieService,
    public dialog: MatDialog
  ) { }

  onSubmit() {
    this.loading = true;
    this.resetErrors();
    this.checkErrors();
    if (this.errors.isdetected) {
      this.loading = false;
      return;
    }

    this.identificationService.registerResquest(this.data, this.step, this.userResponse)
      .subscribe((response) => {
        if (response.success) {
          if (response.value.nextStep === 1) {
            this.errors.step1error = true;
            this.data.message_error_step1 = response.value.frontMessage;
            this.loading = false;
            return;
          }
          if (response.value.nextStep === 2) {
            this.step = 2;
            this.userResponse = 1;
            this.openRegistrationPopup(response, response.nextStep);
            this.loading = false;
            return;
          }
          this.loading = false;
          this.openPrivateCodePopup(response.value.privateCode);
          this.cookieService.set('userId', response.value.userId);
          this.cookieService.set('privateCode', response.value.privateCode);
          this.router.navigate(['homePage']);
        }
        else {
          this.authenticationError = true;
          if(response.value != null) {
            this.authenticationErrorMsg = response.message;
            if(response.value.hasOwnProperty('frontMessage')) {
              this.authenticationErrorMsg = response.value.frontMessage;
            }
          }
          else {
            this.authenticationErrorMsg = "An error occurred, please contact support";
          }
          this.loading = false;
          return;
        }
      });


  }

  openRegistrationPopup(response: any, step: any) {
    const dialogRef = this.dialog.open(YourPopupComponentComponent, {
      data: { data: response },
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result.action == "Submit") {
        this.step == step;
        this.onSubmit();
      }
    });
  }

  openPrivateCodePopup(privateCode: any) {
    const dialogRef = this.dialog.open(ShowPrivateCodeComponent, {
      data: { data: privateCode },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  checkErrors() {
    if (this.data.firstName == "") {
      this.errors.firstName = true;
      this.errors.isdetected = true;
    }
    if (this.data.lastName == "") {
      this.errors.lastName = true;
      this.errors.isdetected = true;
    }
    if (this.data.email == "") {
      this.errors.email = true;
      this.errors.isdetected = true;
    }
    if (this.data.ssn == "") {
      this.errors.ssn = true;
      this.errors.isdetected = true;
    }
    if (this.data.sexe == "") {
      this.errors.sexe = true;
      this.errors.isdetected = true;
    }
    if (this.data.phn == "") {
      this.errors.phn = true;
      this.errors.isdetected = true;
    }
    if (this.data.birthDate == "") {
      this.errors.birthDate = true;
      this.errors.isdetected = true;
    }
    if (this.data.cityofbirth == "") {
      this.errors.cityofbirth = true;
      this.errors.isdetected = true;
    }
    if (this.data.countryofbirth == "") {
      this.errors.countryofbirth = true;
      this.errors.isdetected = true;
    }
    if (this.data.nationality == "") {
      this.errors.nationality = true;
      this.errors.isdetected = true;
    }
    if (this.data.adress == "") {
      this.errors.adress = true;
      this.errors.isdetected = true;
    }
    if (this.data.postalCode == "") {
      this.errors.postalCode = true;
      this.errors.isdetected = true;
    }
    if (this.data.password == "") {
      this.errors.password = true;
      this.errors.isdetected = true;
    }
    if (this.data.password !== this.data.confirmPassword) {
      this.errors.confirmPassword = true
      this.errors.isdetected = true;
    }
  }

  resetErrors() {
    this.errors.isdetected = false;
    this.errors.firstName = false;
    this.errors.lastName = false;
    this.errors.email = false;
    this.errors.ssn = false;
    this.errors.sexe = false;
    this.errors.phn = false;
    this.errors.birthDate = false;
    this.errors.cityofbirth = false;
    this.errors.countryofbirth = false;
    this.errors.nationality = false;
    this.errors.adress = false;
    this.errors.postalCode = false;
    this.errors.password = false;
    this.errors.confirmPassword = false;
  }
}
