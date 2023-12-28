import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowPrivateCodeComponent } from './show-private-code.component';

describe('ShowPrivateCodeComponent', () => {
  let component: ShowPrivateCodeComponent;
  let fixture: ComponentFixture<ShowPrivateCodeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShowPrivateCodeComponent]
    });
    fixture = TestBed.createComponent(ShowPrivateCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
