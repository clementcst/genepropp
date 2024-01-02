import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TreeContentComponent } from './tree-content.component';

describe('TreeContentComponent', () => {
  let component: TreeContentComponent;
  let fixture: ComponentFixture<TreeContentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TreeContentComponent]
    });
    fixture = TestBed.createComponent(TreeContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
