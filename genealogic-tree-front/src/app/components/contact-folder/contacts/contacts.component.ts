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
  loading: boolean = false;
  loadingconv: boolean = false;

  constructor() { }

  ngOnInit(): void { }

  createConversationListener() {
    this.loading = true;
    this.createConversationClick.emit();
  }

  showUsersNotInConversation(usersNotInConv: any[]) {
    this.loading = false;
    this.usersNotInConv = usersNotInConv;
    this.showUsersNotInConv = true;
  }

  selectUser(user: any) {
    this.loadingconv = true;
    this.selectUserClick.emit(user);
    this.showUsersNotInConv = false;
    this.removeUserFromList(user);
  }

  updateLoadingConv(value: boolean) {
    this.loadingconv = value;
  }

  private removeUserFromList(user: any) {
    const index = this.usersNotInConv.indexOf(user);
    if (index !== -1) {
      this.usersNotInConv.splice(index, 1);
    }
  }
}
