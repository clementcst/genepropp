import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialSuccessPopupComponent } from './special-success-popup.component';

describe('SpecialSuccessPopupComponent', () => {
  let component: SpecialSuccessPopupComponent;
  let fixture: ComponentFixture<SpecialSuccessPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpecialSuccessPopupComponent]
    });
    fixture = TestBed.createComponent(SpecialSuccessPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
