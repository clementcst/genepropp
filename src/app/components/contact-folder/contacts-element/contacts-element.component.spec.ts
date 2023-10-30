import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactsElementComponent } from './contacts-element.component';

describe('ContactsElementComponent', () => {
  let component: ContactsElementComponent;
  let fixture: ComponentFixture<ContactsElementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactsElementComponent]
    });
    fixture = TestBed.createComponent(ContactsElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
