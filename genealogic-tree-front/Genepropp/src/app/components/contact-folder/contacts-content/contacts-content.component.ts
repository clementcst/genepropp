import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-contacts-content',
  templateUrl: './contacts-content.component.html',
  styleUrls: ['./contacts-content.component.css']
})
export class ContactsContentComponent implements OnInit{

  contacts!:any;
  currentContact: any;

  constructor(private userService : UserService ) { 
    this.userService = userService;
  }

  ngOnInit(): void {
    this.userService.getUsers().subscribe((data) => {
      this.contacts = data;
      this.currentContact = this.contacts[0];
    });
    
  }

  openChat(contact: any) {
    this.currentContact = contact;
  }
}
