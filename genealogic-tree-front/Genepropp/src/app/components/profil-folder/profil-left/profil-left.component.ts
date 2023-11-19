import { Component, OnInit } from '@angular/core';
import { TreeService } from '../../../services/tree/tree.service';

@Component({
  selector: 'app-profil-left',
  templateUrl: './profil-left.component.html',
  styleUrls: ['./profil-left.component.css']
})
export class ProfilLeftComponent implements OnInit {
  tree: any = {};
  boxs: any[] = [];

  constructor(private treeService : TreeService ) { 
    this.treeService = treeService;
  }

  ngOnInit(): void {
    this.treeService.getTree(1).subscribe((data) => {
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
  }
}