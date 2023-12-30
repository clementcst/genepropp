import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class CookieManagementService {
  constructor(private cookieService: CookieService) {}

  savePPUrl(profilPictureUrl: string): void {
    this.cookieService.set('profilPictureUrl', profilPictureUrl);
  }

  getPPUrl() {
    return this.cookieService.get('profilPictureUrl');
  }
}
