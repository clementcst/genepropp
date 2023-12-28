import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user/user.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {


  pages=[
    {link:"/homePage", displayName:"Home"},
    {link:"/myTreePage", displayName:"My Tree"},
    {link:"/contactPage", displayName:"Contacts"},
    {link:"/directoryPage", displayName:"Directory"},
    {link:"/profilPage", displayName:"Profil"},
    { link: "/", displayName: "Log out"}
  ]
  user: any = {};

  constructor(private userService : UserService, private cookieService: CookieService) { 
    this.userService = userService;
    this.cookieService = cookieService;
  }

  ngOnInit() : void{ 
    this.userService.getUser(this.cookieService.get('userId')).subscribe((data) => {
      this.user = data.value;
      console.log(this.user)
    });
  }

}
