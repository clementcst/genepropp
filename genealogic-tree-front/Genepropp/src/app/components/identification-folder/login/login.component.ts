import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { IdentificationService } from '../../../services/identificaton/identification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  data: any = {
    privatecode: '',
    password: ''
  }
  errors: any = {
    isdetected: false,
    privatecode: false,
    password: false
  }
  authenticationError: boolean = false;
  errorMessage: string = '';

  constructor(
    private identificationService: IdentificationService,
    private router: Router,
    private cookieService: CookieService
    ) {}

  ngOnInit(): void {
    this.authenticationError = false;
  }

  onSubmit() {
    this.resetErrors();
    this.checkErrors();
    if(this.errors.isdetected) return;

    this.identificationService.loginattempt(this.data)
      .subscribe((response) => {
        if (response.success) {
          this.cookieService.set('userId', response.value);
          this.cookieService.set('privateCode', "a faire");
          this.router.navigate(['homePage']);
        }
        else {
          this.authenticationError = true;
          this.errorMessage = response.message;
        }
      });
  }

  checkErrors() {
    if (this.data.privatecode == "") {
      this.errors.privatecode = true;
      this.errors.isdetected = true;
    }
    if (this.data.password == "") {
      this.errors.password = true;
      this.errors.isdetected = true;
    }
  }

  resetErrors() {
    this.errors.isdetected = false;
    this.errors.privatecode = false;
    this.errors.password = false;
  }
}
