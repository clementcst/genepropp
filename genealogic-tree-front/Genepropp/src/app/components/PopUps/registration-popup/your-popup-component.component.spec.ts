import { ComponentFixture, TestBed } from '@angular/core/testing';

import { YourPopupComponentComponent } from './your-popup-component.component';

describe('YourPopupComponentComponent', () => {
  let component: YourPopupComponentComponent;
  let fixture: ComponentFixture<YourPopupComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [YourPopupComponentComponent]
    });
    fixture = TestBed.createComponent(YourPopupComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
