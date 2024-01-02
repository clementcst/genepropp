import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilInputsComponent } from './profil-inputs.component';

describe('ProfilInputsComponent', () => {
  let component: ProfilInputsComponent;
  let fixture: ComponentFixture<ProfilInputsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfilInputsComponent]
    });
    fixture = TestBed.createComponent(ProfilInputsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
