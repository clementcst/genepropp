import { Component } from '@angular/core';

@Component({
  selector: 'app-contacts-content',
  templateUrl: './contacts-content.component.html',
  styleUrls: ['./contacts-content.component.css']
})
export class ContactsContentComponent {

  contacts = [
    { firstName: "Sophie", lastName: "Martin", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Sophie", lastName: "Martin", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Thomas", lastName: "Dupont", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Emma", lastName: "Dubois", url_photo: "../assets/media/profil.jpeg" },
    { firstName: "Lucas", lastName: "Lefebvre", url_photo: "../assets/media/profil.jpeg" }
  ];

  currentContact: any;

  ngOnInit() {
    // Ouvrir le chat du premier contact à l'ouverture de la page
    this.currentContact = this.contacts[0];
  }

  openChat(contact: any) {
    this.currentContact = contact;
  }
}
