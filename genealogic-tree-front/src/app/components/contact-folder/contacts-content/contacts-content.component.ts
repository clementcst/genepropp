// Importez les modules nécessaires
import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../../services/user/user.service';
import { ConversationService } from '../../../services/conversation/conversation.service';
import { ContactsComponent } from '../contacts/contacts.component'
import { CookieService } from 'ngx-cookie-service';
import { forkJoin, Observable } from 'rxjs';
import { switchMap, map } from 'rxjs/operators';

@Component({
  selector: 'app-contacts-content',
  templateUrl: './contacts-content.component.html',
  styleUrls: ['./contacts-content.component.css']
})
export class ContactsContentComponent implements OnInit {

  infoMe!: any;
  valueOfConv!: any;
  currentContact: any;
  otherContactId: any;
  contactsForFront: { senderInfo: any, convId: string }[] = [];
  idAlreadyInConv: string[] = [];
  users: any[] = [];
  @ViewChild('contactsComponent') contactsComponent: ContactsComponent | undefined;
  newConversationId: any;

  constructor(private userService: UserService, private conversationService: ConversationService, private cookieService: CookieService) {
    this.cookieService = cookieService;
    this.userService = userService;
    this.conversationService = conversationService;
  }

  ngOnInit(): void {
    this.userService.getUser(this.cookieService.get('userId')).pipe(
      switchMap((data: any) => {
        this.infoMe = data.value;

        const conversationRequests: Observable<{ senderInfo: any, convId: string }>[] = this.infoMe.conversationsId.map((conversationId: string) => {
          return this.conversationService.getConversation(conversationId).pipe(
            switchMap((conversationData: any) => {
              this.valueOfConv = conversationData.value;
              this.otherContactId = this.valueOfConv.userId1 != this.cookieService.get('userId') ? this.valueOfConv.userId1 : this.valueOfConv.userId2;
              if (this.otherContactId) {
                this.idAlreadyInConv.push(this.otherContactId);
              }
              return this.userService.getUser(this.otherContactId);
            }),
            map((data2: any) => {
              return { senderInfo: data2.value, convId: conversationId };
            })
          );
        });

        return forkJoin(conversationRequests);
      })
    ).subscribe((contactsForFront: { senderInfo: any, convId: string }[]) => {
      this.contactsForFront = contactsForFront;
      this.currentContact = this.contactsForFront[0];
    });
  }

  createConversation() {
    let usersNotInConv: any[] = [];
    this.userService.getUsers().subscribe((data) => {
      this.users = data.value;
      this.users.forEach((user) => {
        if (!this.idAlreadyInConv.includes(user.id)) {
          usersNotInConv.push(user);
        }
      });
      if (this.contactsComponent) {
        this.contactsComponent.showUsersNotInConversation(usersNotInConv);
      }
    });
  }

  openChat(contact: any) {
    this.currentContact = contact;
  }

  handleSelectUser(user: any) {
    this.conversationService.newConversation(this.infoMe.id, user.id)
      .subscribe((response) => {
        this.newConversationId = response.value;
        let newContact = { senderInfo: user, convId: this.newConversationId};
        this.contactsForFront.push(newContact);
        this.idAlreadyInConv.push(user.id);
        if (this.contactsComponent) {
          this.contactsComponent.updateLoadingConv(false);
        }
        this.openChat(newContact);
      });
  }
}
