import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyTreePageComponent } from './my-tree-page.component';

describe('MyTreePageComponent', () => {
  let component: MyTreePageComponent;
  let fixture: ComponentFixture<MyTreePageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyTreePageComponent]
    });
    fixture = TestBed.createComponent(MyTreePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
