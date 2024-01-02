import { Component, Input, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-nav-element',
  templateUrl: './nav-element.component.html',
  styleUrls: ['./nav-element.component.css']
})
export class NavElementComponent implements OnInit {
  @Input() page!: any;

  constructor(private cookieService: CookieService) { }

  ngOnInit(): void { }

  logout(): void {
    this.cookieService.delete('userId');
    this.cookieService.delete('privateCode');
    this.cookieService.delete('profilPictureUrl');
  }

}
