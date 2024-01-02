import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorMergeTreePopupComponent } from './error-merge-tree-popup.component';

describe('ErrorMergeTreePopupComponent', () => {
  let component: ErrorMergeTreePopupComponent;
  let fixture: ComponentFixture<ErrorMergeTreePopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ErrorMergeTreePopupComponent]
    });
    fixture = TestBed.createComponent(ErrorMergeTreePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
