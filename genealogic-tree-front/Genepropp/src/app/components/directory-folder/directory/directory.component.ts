import { Component } from '@angular/core';

@Component({
  selector: 'app-directory',
  templateUrl: './directory.component.html',
  styleUrls: ['./directory.component.css']
})
export class DirectoryComponent {



  users = [
    { firstName: "Sophie", lastName: "Martin", age: "28", treeSize: "42", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", age: "35", treeSize: "60", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", age: "23", treeSize: "30", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", age: "28", treeSize: "42", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", age: "35", treeSize: "60", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", age: "23", treeSize: "30", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", age: "28", treeSize: "42", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", age: "35", treeSize: "60", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", age: "23", treeSize: "30", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", age: "28", treeSize: "42", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", age: "35", treeSize: "60", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", age: "23", treeSize: "30", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", age: "28", treeSize: "42", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", age: "35", treeSize: "60", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", age: "23", treeSize: "30", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", age: "28", treeSize: "42", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", age: "35", treeSize: "60", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", age: "23", treeSize: "30", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", age: "28", treeSize: "42", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", age: "35", treeSize: "60", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", age: "23", treeSize: "30", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Lucas", lastName: "Lefebvre", age: "31", treeSize: "55", url_photo: "../assets/media/profil.jpeg" }
  ];
  
  
  
  

  constructor() { }

  ngOnInit() : void{ }


}
