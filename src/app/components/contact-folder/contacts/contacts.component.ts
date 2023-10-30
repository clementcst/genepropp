import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent {


  @Input() contacts: any; // Données de contact
  @Output() contactClick = new EventEmitter<any>();

  constructor() { }

  ngOnInit() : void{ }

}
