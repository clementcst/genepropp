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
  loading: boolean = false;

  constructor(
    private identificationService: IdentificationService,
    private router: Router,
    private cookieService: CookieService
    ) {}

  ngOnInit(): void {
    this.authenticationError = false;
  }

  onSubmit() {
    this.loading = true;
    this.resetErrors();
    this.checkErrors();
    if(this.errors.isdetected) {
      this.loading = false;
      return;
    }

    this.identificationService.loginattempt(this.data)
      .subscribe((response) => {
        if (response.success) {
          this.loading = false;
          this.cookieService.set('userId', response.value.userId);
          this.cookieService.set('privateCode', response.value.privateCode);
          this.router.navigate(['homePage']);
        }
        else {
          this.loading = false;
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
