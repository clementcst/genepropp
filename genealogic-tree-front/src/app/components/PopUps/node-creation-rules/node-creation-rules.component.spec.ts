import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NodeCreationRulesComponent } from './node-creation-rules.component';

describe('NodeCreationRulesComponent', () => {
  let component: NodeCreationRulesComponent;
  let fixture: ComponentFixture<NodeCreationRulesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NodeCreationRulesComponent]
    });
    fixture = TestBed.createComponent(NodeCreationRulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
