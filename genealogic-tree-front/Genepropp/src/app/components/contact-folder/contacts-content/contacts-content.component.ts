import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-contacts-content',
  templateUrl: './contacts-content.component.html',
  styleUrls: ['./contacts-content.component.css']
})
export class ContactsContentComponent implements OnInit{

  contacts: any[] = [];
  currentContact: any;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/users').subscribe((data) => {
      this.contacts = data;
      this.currentContact = this.contacts[0];
    });
  }

  openChat(contact: any) {
    this.currentContact = contact;
  }
}
