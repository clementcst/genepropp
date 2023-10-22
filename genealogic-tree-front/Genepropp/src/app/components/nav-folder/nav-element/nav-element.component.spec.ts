import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavElementComponent } from './nav-element.component';

describe('NavElementComponent', () => {
  let component: NavElementComponent;
  let fixture: ComponentFixture<NavElementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavElementComponent]
    });
    fixture = TestBed.createComponent(NavElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
