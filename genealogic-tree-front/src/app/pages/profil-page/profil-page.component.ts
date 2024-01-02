import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profil-page',
  templateUrl: './profil-page.component.html',
  styleUrls: ['./profil-page.component.css']
})
export class ProfilPageComponent implements OnInit {
  isUserIdValid: boolean = true;

  constructor(private cookieService: CookieService, private router: Router) { }

  ngOnInit(): void {
    if (!this.cookieService.get('userId')) {
      this.isUserIdValid = false;
      this.router.navigate(['/']);
    }
  }
}
