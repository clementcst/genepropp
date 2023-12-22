import { Component, OnInit } from '@angular/core';
import { TreeService } from '../../../services/tree/tree.service';
import { UserService } from '../../../services/user/user.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-profil-left',
  templateUrl: './profil-left.component.html',
  styleUrls: ['./profil-left.component.css']
})
export class ProfilLeftComponent implements OnInit {
  tree: any = {};
  boxs: any[] = [];
  user: any = {};

  constructor(private treeService : TreeService, private userService : UserService, private cookieService: CookieService) { 
    this.treeService = treeService;
    this.cookieService = cookieService;
    this.userService = userService;
  }

  ngOnInit(): void {
    this.treeService.getTree(this.cookieService.get('userId')).subscribe((data) => {
      this.tree = data.value;
      this.boxs = [
        { title: "Month views", value: this.tree.viewOfMonth },
        { title: "Annual views", value: this.tree.viewOfYear },
        { title: "Tree length", value: this.tree.id }
      ];

      if (this.tree.treePublic) {
        const visibilityRadio = document.getElementById('inline-radio-public') as HTMLInputElement;
        visibilityRadio.checked = true;
      } 
      else {
        const visibilityRadio = document.getElementById('inline-radio-private') as HTMLInputElement;
        visibilityRadio.checked = true;
      }
    });

    this.userService.getUser(this.cookieService.get('userId')).subscribe((data) => {
      this.user = data.value;
    });
  }
}