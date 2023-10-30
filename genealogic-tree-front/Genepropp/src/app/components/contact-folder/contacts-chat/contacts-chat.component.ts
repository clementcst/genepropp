import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-contacts-chat',
  templateUrl: './contacts-chat.component.html',
  styleUrls: ['./contacts-chat.component.css']
})
export class ContactsChatComponent {
  @Input() contact: any; //
}
