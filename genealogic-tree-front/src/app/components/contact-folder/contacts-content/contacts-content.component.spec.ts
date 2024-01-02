import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactsContentComponent } from './contacts-content.component';

describe('ContactsContentComponent', () => {
  let component: ContactsContentComponent;
  let fixture: ComponentFixture<ContactsContentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactsContentComponent]
    });
    fixture = TestBed.createComponent(ContactsContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
