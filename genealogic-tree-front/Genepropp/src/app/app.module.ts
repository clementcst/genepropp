import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from 'src/app/components/nav/nav.component';
import { NavElementComponent } from './components/nav-element/nav-element.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LoginComponent } from './components/login/login.component';
import { ContactPageComponent } from './pages/contact-page/contact-page.component';
import { MyTreePageComponent } from './pages/my-tree-page/my-tree-page.component';
import { DirectoryPageComponent } from './pages/directory-page/directory-page.component';
import { ProfilPageComponent } from './pages/profil-page/profil-page.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { RegistrationPageComponent } from './pages/registration-page/registration-page.component';
import { HomeContentComponent } from './components/home-content/home-content.component';
import { ProfilHeaderComponent } from './components/profil-header/profil-header.component';



@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    NavElementComponent,
    LoginPageComponent,
    HomePageComponent,
    LoginComponent,
    ContactPageComponent,
    MyTreePageComponent,
    DirectoryPageComponent,
    ProfilPageComponent,
    RegistrationComponent,
    RegistrationPageComponent,
    HomeContentComponent
    ProfilHeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
