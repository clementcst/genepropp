import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';


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

import { HttpClientModule } from '@angular/common/http';
import { YourPopupComponentComponent } from './components/PopUps/registration-popup/your-popup-component.component';
import { ShowPrivateCodeComponent } from './components/PopUps/show-private-code-popup/show-private-code.component';

import { TreeContentComponent } from './components/tree-folder/tree-content/tree-content.component';
import { LinkedHashMap } from './components/tree-folder/linked-hashmap/linked-hashmap.component';
import { NodeCreationRulesComponent } from './components/PopUps/node-creation-rules/node-creation-rules.component';
import { ErrorMergeTreePopupComponent } from './components/PopUps/error-merge-tree-popup/error-merge-tree-popup.component';
import { SpecialSuccessPopupComponent } from './components/PopUps/special-success-popup/special-success-popup.component';



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
    YourPopupComponentComponent,
    ShowPrivateCodeComponent,
    TreeContentComponent,
    LinkedHashMap,
    NodeCreationRulesComponent,
    ErrorMergeTreePopupComponent,
    SpecialSuccessPopupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
