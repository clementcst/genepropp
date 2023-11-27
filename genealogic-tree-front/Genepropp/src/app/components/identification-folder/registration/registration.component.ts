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

  constructor(
    private identificationService: IdentificationService,
    private router: Router,
    private cookieService: CookieService
  ) {}

  onSubmit() {
    //Verifie si les mots de passe correspondent
    if (this.data.password !== this.data.confirmPassword) {
      return;
    }

    this.identificationService.registerResquest(this.data, this.step)
      .subscribe((response) => {
        if (response.success) {
          console.log(response)
          this.cookieService.set('userId', response.value);
          this.router.navigate(['homePage']);
        }
        else {
          this.authenticationError = true;
          console.error(response);
        }
      });

  }
}
