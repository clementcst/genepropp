import { Component } from '@angular/core';

@Component({
  selector: 'app-tree-content',
  templateUrl: './tree-content.component.html',
  styleUrls: ['./tree-content.component.css']
})
export class TreeContentComponent {

  Childs2 = [
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  },
    { firstName: "Sophie", lastName: "Martin", age:"12", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  }
  ];

  Childs3 = [
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  },
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  },
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  },
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  },
    { firstName: "Sophie", lastName: "Martin", age:"12", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  }
  ];

  Erwanchild = [
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  },
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  },
    { firstName: "Sophie", lastName: "Martin", age:"12", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null  }
  ];

  Childs = [
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: this.Childs2, parents: null },
    { firstName: "Erwan", lastName: "Clement", age:"12", url_photo: "../assets/media/profil.jpeg", childs: this.Erwanchild, parents: null },
    { firstName: "Mathieu", lastName: "Jonh", age:"31", url_photo: "../assets/media/profil.jpeg", childs: this.Childs3, parents: null},
    { firstName: "Sophie", lastName: "Martin", age:"12", url_photo: "../assets/media/profil.jpeg", childs: null, parents: null }
  ];

  node = 
    { firstName: "Sophie", lastName: "Martin", age:"42", url_photo: "../assets/media/profil.jpeg", childs: this.Childs, parents: null };


  
}
