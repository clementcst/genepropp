import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilRigthComponent } from './profil-rigth.component';

describe('ProfilRigthComponent', () => {
  let component: ProfilRigthComponent;
  let fixture: ComponentFixture<ProfilRigthComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfilRigthComponent]
    });
    fixture = TestBed.createComponent(ProfilRigthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
