import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DirectoryElementComponent } from './directory-element.component';

describe('DirectoryElementComponent', () => {
  let component: DirectoryElementComponent;
  let fixture: ComponentFixture<DirectoryElementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DirectoryElementComponent]
    });
    fixture = TestBed.createComponent(DirectoryElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
