import { Component } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {


  pages=[
    {link:"/homePage", displayName:"Home"},
    {link:"/myTreePage", displayName:"My Tree"},
    {link:"/contactPage", displayName:"Contacts"},
    {link:"/directoryPage", displayName:"Directory"},
    {link:"/profilPage", displayName:"Profil"}
  ]

  constructor() { }

  ngOnInit() : void{ }

}
