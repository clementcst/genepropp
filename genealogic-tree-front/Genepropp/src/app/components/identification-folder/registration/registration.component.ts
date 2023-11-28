import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { IdentificationService } from '../../../services/identificaton/identification.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  authenticationError: boolean = false;
  step=1;
  // Déclarer des variables pour stocker les valeurs des champs
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
    confirmPassword: ''
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
    confirmPassword: false
  };

  constructor(
    private identificationService: IdentificationService,
    private router: Router,
    private cookieService: CookieService
  ) {}

  onSubmit() {
    this.resetErrors();
    this.checkErrors();
    if(this.errors.isdetected) return;

    this.identificationService.registerResquest(this.data, this.step)
      .subscribe((response) => {
        console.log(response)
        if (response.success) {
          this.cookieService.set('userId', response.value);
          this.router.navigate(['homePage']);
        }
        else {
          this.authenticationError = true;
        }
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
