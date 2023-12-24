import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-contact-page',
  templateUrl: './contact-page.component.html',
  styleUrls: ['./contact-page.component.css']
})
export class ContactPageComponent implements OnInit {
  isUserIdValid: boolean = true;

  constructor(private cookieService: CookieService, private router: Router) { }

  ngOnInit(): void {
    if (!this.cookieService.get('userId')) {
      this.isUserIdValid = false;
      this.router.navigate(['/']);
    }
  }
}
