import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactsChatComponent } from './contacts-chat.component';

describe('ContactsChatComponent', () => {
  let component: ContactsChatComponent;
  let fixture: ComponentFixture<ContactsChatComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactsChatComponent]
    });
    fixture = TestBed.createComponent(ContactsChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
