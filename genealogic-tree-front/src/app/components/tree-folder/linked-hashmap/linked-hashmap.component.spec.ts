import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkedHashmapComponent } from './linked-hashmap.component';

describe('LinkedHashmapComponent', () => {
  let component: LinkedHashmapComponent;
  let fixture: ComponentFixture<LinkedHashmapComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LinkedHashmapComponent]
    });
    fixture = TestBed.createComponent(LinkedHashmapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
