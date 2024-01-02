import { Component, Input, ViewChild, ElementRef, OnInit, AfterViewChecked, OnChanges, SimpleChanges, HostListener } from '@angular/core';
import { ConversationService } from '../../../services/conversation/conversation.service';
import { UserService } from '../../../services/user/user.service';
import { CookieService } from 'ngx-cookie-service';

interface Contact {
  senderInfo: {
    firstName: string;
    lastName: string;
    profilPictureUrl: string;
    id: string;
  };
  convId: string;
  id: string;
  // Add other properties as needed
}

@Component({
  selector: 'app-contacts-chat',
  templateUrl: './contacts-chat.component.html',
  styleUrls: ['./contacts-chat.component.css']
})
export class ContactsChatComponent implements OnInit, AfterViewChecked, OnChanges {
  @Input() contact!: Contact;
  @ViewChild('messageInput') messageInput!: ElementRef;
  @ViewChild('chatContainer') chatContainer!: ElementRef;
  messagetab: any = {};
  myInfo : any = {};
  validationSuccess: boolean = false;
  message: string = "";
  loading: boolean = false;
  loadingpage: boolean = false;

  constructor(private conversationService: ConversationService, private userService: UserService, private cookieService: CookieService) {
    this.cookieService = cookieService;
    this.conversationService = conversationService;
    this.userService = userService;
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadingpage = true;
    if (changes['contact'] && !changes['contact'].firstChange) {
      this.receiveMessage();
    }
  }
  ngAfterViewChecked(): void {
    this.scrollToBottom();
  }
  private scrollToBottom(): void {
    if (this.chatContainer) {
      const containerElement = this.chatContainer.nativeElement;
      containerElement.scrollTop = containerElement.scrollHeight;
    }
  }

  receiveMessage() {
    if (this.contact) {
      this.conversationService.getConversation(this.contact.convId).subscribe((data) => {
        this.messagetab = data.value;
        this.loadingpage = false;
        this.messagetab.messages.sort((a: any, b: any) => {
          const dateA = new Date(a.messageDateTime).getTime();
          const dateB = new Date(b.messageDateTime).getTime();
          return dateA - dateB;
        });
        console.log(this.messagetab)
      });
      this.loading = false;
    }
  }

  validationUser(endpoints: string) {
    this.conversationService.validateUser(endpoints).subscribe((data) => {
      this.validationSuccess = data.success;
      if (this.validationSuccess) {
        this.receiveMessage();
      }
    });
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter' && event.target instanceof HTMLTextAreaElement) {
      if (!event.shiftKey) {
        event.preventDefault();
      }
    }
  }

  sendMessage() {
    this.loading = true;
    const myId = parseFloat(this.cookieService.get('userId'));
    let otherContactId: number;
    if (this.messagetab.userId1 == myId) {
      otherContactId = this.messagetab.userId2;
    }
    else {
      otherContactId = this.messagetab.userId1;
    }
    const messageContent = this.messageInput.nativeElement.value;
    if (messageContent) {
      this.conversationService.newMessage(myId, otherContactId, messageContent)
        .subscribe(response => {
          this.receiveMessage();
          if (!response.success) {
            this.loading = false;
            return
          }
        });
      this.messageInput.nativeElement.value = '';
    }
  }

  ngOnInit(): void {
    this.receiveMessage();
    this.userService.getUser(this.cookieService.get('userId')).subscribe((data) => {
      this.myInfo = data.value;
    });
  }
}
