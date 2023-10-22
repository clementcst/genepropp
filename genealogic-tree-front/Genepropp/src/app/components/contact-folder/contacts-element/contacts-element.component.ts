import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-contacts-element',
  templateUrl: './contacts-element.component.html',
  styleUrls: ['./contacts-element.component.css']
})
export class ContactsElementComponent {

  @Input()
   
  contact!:any;
  constructor() { }

  ngOnInit() : void{ }

}
