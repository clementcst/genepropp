import { Component, OnInit } from '@angular/core';
import { CookieManagementService } from '../../../services/cookies/cookie.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  PPurl: string = "";

  pages=[
    {link:"/myTreePage", displayName:"My Tree"},
    {link:"/contactPage", displayName:"Contacts"},
    {link:"/directoryPage", displayName:"Directory"},
    {link:"/profilPage", displayName:"Profil"},
    { link: "/", displayName: "Log out"}
  ]

  constructor(private cookieService: CookieService, private cookieManagementService: CookieManagementService) { 
    this.cookieService = cookieService;
    this.cookieManagementService= cookieManagementService;
  }

  ngOnInit() : void{ 
    this.getPPUrl();
  }

  getPPUrl() {
    this.PPurl = this.cookieManagementService.getPPUrl();
  }

}
