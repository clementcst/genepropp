import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-directory-page',
  templateUrl: './directory-page.component.html',
  styleUrls: ['./directory-page.component.css']
})
export class DirectoryPageComponent implements OnInit {
  isUserIdValid: boolean = true;

  constructor(private cookieService: CookieService, private router: Router) { }

  ngOnInit(): void {
    if (!this.cookieService.get('userId')) {
      this.isUserIdValid = false;
      this.router.navigate(['/']);
    }
  }
}
