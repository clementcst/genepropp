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
  @Output() selectUserClick = new EventEmitter<any>();
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

  selectUser(user: any) {
    this.selectUserClick.emit(user);
    this.showUsersNotInConv = false;
    this.removeUserFromList(user);
  }

  private removeUserFromList(user: any) {
    const index = this.usersNotInConv.indexOf(user);
    if (index !== -1) {
      this.usersNotInConv.splice(index, 1);
    }
  }
}
