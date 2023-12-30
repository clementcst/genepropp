import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { CookieManagementService } from '../../services/cookies/cookie.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-home-content',
  templateUrl: './home-content.component.html',
  styleUrls: ['./home-content.component.css']
})
export class HomeContentComponent implements OnInit {
  user: any = {};

  constructor(private cookieService: CookieService, private userService: UserService, private cookieManagementService: CookieManagementService) {}

  ngOnInit(): void {
    this.userService.getUser(this.cookieService.get('userId')).subscribe((data) => {
      this.user = data.value;
      this.cookieManagementService.savePPUrl(this.user.profilPictureUrl);
    });
  }
}
