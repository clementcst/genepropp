import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  isUserIdValid: boolean = true;

  constructor(private cookieService: CookieService, private router: Router) { }

  ngOnInit(): void {
    if (!this.cookieService.get('userId')) {
      this.isUserIdValid = false;
      this.router.navigate(['/']);
    }
  }
}
