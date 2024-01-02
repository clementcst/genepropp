import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilLeftComponent } from './profil-left.component';

describe('ProfilLeftComponent', () => {
  let component: ProfilLeftComponent;
  let fixture: ComponentFixture<ProfilLeftComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfilLeftComponent]
    });
    fixture = TestBed.createComponent(ProfilLeftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
