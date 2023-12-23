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
  changePictureFormVisible: boolean = false;
  newPictureUrl: string = '';
  showSuccessMessage: boolean = false;
  successMessage: string = '';
  showFailedMessage: boolean = false;
  failedMessage: string = '';

  constructor(private treeService : TreeService, private userService : UserService, private cookieService: CookieService) { 
    this.treeService = treeService;
    this.cookieService = cookieService;
    this.userService = userService;
  }

  ngOnInit(): void {
    this.showUserProfil()
  }

  private showUserProfil() {
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

  showChangePictureForm() {
    this.changePictureFormVisible = true;
  }

  submitPicture() {
    const inputsData: any = {};
    inputsData.profilPictureUrl = this.newPictureUrl;
    this.userService.updateUser(this.user.id, inputsData).subscribe(response => {
      if(response.success) {
        this.successMessage = response.message || 'Modification successful.';
        this.showSuccessMessage = true;
        setTimeout(() => {
          this.showSuccessMessage = false;
        }, 3000);
        this.showUserProfil()
      }
      else {
        this.failedMessage = response.message || 'Modification failed.';
        this.showFailedMessage = true;
        setTimeout(() => {
          this.showFailedMessage = false;
        }, 3000);
      }
    });
    this.changePictureFormVisible = false;
  }

  cancelChangePicture() {
    this.changePictureFormVisible = false;
    this.newPictureUrl = '';
  }
}