import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilStatboxComponent } from './profil-statbox.component';

describe('ProfilStatboxComponent', () => {
  let component: ProfilStatboxComponent;
  let fixture: ComponentFixture<ProfilStatboxComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfilStatboxComponent]
    });
    fixture = TestBed.createComponent(ProfilStatboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
