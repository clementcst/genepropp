import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TreeService } from '../../../services/tree/tree.service';
import { UserService } from '../../../services/user/user.service';
import { CookieManagementService } from '../../../services/cookies/cookie.service';
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
  treeVisibilityControl = new FormControl();
  showSuccessMessageTree: boolean = false;
  successMessageTree: string = '';
  showFailedMessageTree: boolean = false;
  failedMessageTree: string = '';
  loadingPicture: boolean = false;
  loadingTree: boolean = false;
  loadingpage: boolean = false;

  constructor(private treeService : TreeService, private userService : UserService, private cookieService: CookieService, private cookieManagementService: CookieManagementService) { 
    this.treeService = treeService;
    this.cookieService = cookieService;
    this.userService = userService;
  }

  ngOnInit(): void {
    this.loadingpage = true;
    this.showUserProfil();
  }

  private showUserProfil() {
    this.treeService.getTree(this.cookieService.get('userId')).subscribe((data) => {
      this.tree = data.value;
      this.boxs = [
        { title: "Month views", value: this.tree.viewOfMonth },
        { title: "Annual views", value: this.tree.viewOfYear },
        { title: "Tree length", value: this.tree.nodes.length }
      ];
      this.loadingpage = false;
      this.userService.getUser(this.cookieService.get('userId')).subscribe((data) => {
        this.user = data.value;
        if (this.user.isMyTreePublic) {
          const visibilityRadio = document.getElementById('inline-radio-public') as HTMLInputElement;
          visibilityRadio.checked = true;
        }
        else {
          const visibilityRadio = document.getElementById('inline-radio-private') as HTMLInputElement;
          visibilityRadio.checked = true;
        }
      });
    });
  }

  showChangePictureForm() {
    this.changePictureFormVisible = true;
  }

  submitPicture() {
    this.loadingPicture = true;
    const inputsData: any = {};
    inputsData.profilPictureUrl = this.newPictureUrl;
    this.userService.updateUser(this.user.id, inputsData).subscribe(response => {
      if(response.success) {
        this.loadingPicture = false;
        this.successMessage = response.message || 'Modification successful.';
        this.showSuccessMessage = true;
        this.newPictureUrl = '';
        setTimeout(() => {
          this.showSuccessMessage = false;
        }, 3000);
        this.user.profilPictureUrl = inputsData.profilPictureUrl
        this.cookieManagementService.savePPUrl(this.user.profilPictureUrl);
      }
      else {
        this.loadingPicture = false;
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

  updateTreeVisibility(value: number) {
    this.treeVisibilityControl.setValue(value);
  }

  logTreeVisibility() {
    this.loadingTree = true;
    const inputsData: any = {};
    inputsData.treePrivacy = this.treeVisibilityControl.value;
    this.userService.updateUser(this.user.id, inputsData).subscribe(response => {
      if(response.success) {
        this.loadingTree = false;
        this.successMessageTree = response.message || 'Modification successful.';
        this.showSuccessMessageTree = true;
        setTimeout(() => {
          this.showSuccessMessageTree = false;
        }, 3000);
        this.showUserProfil()
      }
      else {
        this.loadingTree = false;
        this.failedMessageTree = response.message || 'Modification failed.';
        this.showFailedMessageTree = true;
        setTimeout(() => {
          this.showFailedMessageTree = false;
        }, 3000);
      }
    });
  }
}
