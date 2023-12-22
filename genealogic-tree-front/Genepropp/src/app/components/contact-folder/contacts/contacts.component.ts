import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent {

  @Input() contacts: any;
  @Output() contactClick = new EventEmitter<any>();
  @Output() createConversationClick = new EventEmitter<void>();
  showUsersNotInConv: boolean = false;
  usersNotInConv: any[] = [];

  constructor() { }

  ngOnInit(): void { }

  createConversationListener() {
    this.createConversationClick.emit();
  }

  showUsersNotInConversation(usersNotInConv: any[]) {
    this.usersNotInConv = usersNotInConv;
    this.showUsersNotInConv = true;
  }
}
