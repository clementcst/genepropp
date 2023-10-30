import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilContentComponent } from './profil-content.component';

describe('ProfilContentComponent', () => {
  let component: ProfilContentComponent;
  let fixture: ComponentFixture<ProfilContentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfilContentComponent]
    });
    fixture = TestBed.createComponent(ProfilContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
