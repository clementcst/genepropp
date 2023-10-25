import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from 'src/app/components/nav-folder/nav/nav.component';
import { NavElementComponent } from './components/nav-folder/nav-element/nav-element.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LoginComponent } from './components/identification-folder/login/login.component';
import { ContactPageComponent } from './pages/contact-page/contact-page.component';
import { MyTreePageComponent } from './pages/my-tree-page/my-tree-page.component';
import { DirectoryPageComponent } from './pages/directory-page/directory-page.component';
import { ProfilPageComponent } from './pages/profil-page/profil-page.component';
import { RegistrationComponent } from './components/identification-folder/registration/registration.component';
import { RegistrationPageComponent } from './pages/registration-page/registration-page.component';
import { HomeContentComponent } from './components/home-content/home-content.component';
import { FooterComponent } from './components/footer/footer.component';
import { DirectoryComponent } from './components/directory-folder/directory/directory.component';
import { DirectoryElementComponent } from './components/directory-folder/directory-element/directory-element.component';
import { DirectoryContentComponent } from './components/directory-folder/directory-content/directory-content.component';
import { ContactsComponent } from './components/contact-folder/contacts/contacts.component';
import { ContactsElementComponent } from './components/contact-folder/contacts-element/contacts-element.component';
import { ContactsContentComponent } from './components/contact-folder/contacts-content/contacts-content.component';
import { ContactsChatComponent } from './components/contact-folder/contacts-chat/contacts-chat.component';
import { ProfilContentComponent } from './components/profil-folder/profil-content/profil-content.component';
import { ProfilLeftComponent } from './components/profil-folder/profil-left/profil-left.component';
import { ProfilRigthComponent } from './components/profil-folder/profil-rigth/profil-rigth.component';
import { ProfilStatboxComponent } from './components/profil-folder/profil-statbox/profil-statbox.component';
import { ProfilInputsComponent } from './components/profil-folder/profil-inputs/profil-inputs.component';



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
    HomeContentComponent,
    FooterComponent,
    DirectoryComponent,
    DirectoryElementComponent,
    DirectoryContentComponent,
    ContactsComponent,
    ContactsElementComponent,
    ContactsContentComponent,
    ContactsChatComponent,
    ProfilContentComponent,
    ProfilLeftComponent,
    ProfilRigthComponent,
    ProfilStatboxComponent,
    ProfilInputsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
