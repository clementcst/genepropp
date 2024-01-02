import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { MyTreePageComponent } from './pages/my-tree-page/my-tree-page.component';
import { ContactPageComponent } from './pages/contact-page/contact-page.component';
import { DirectoryPageComponent } from './pages/directory-page/directory-page.component';
import { ProfilPageComponent } from './pages/profil-page/profil-page.component';
import { RegistrationPageComponent } from './pages/registration-page/registration-page.component';

const routes: Routes = [
  { path: '', component: LoginPageComponent },   // Chemin par défaut (page de login)
  { path: 'homePage', component: HomePageComponent },  
  { path: 'myTreePage', component: MyTreePageComponent },   
  { path: 'contactPage', component: ContactPageComponent },   
  { path: 'directoryPage', component: DirectoryPageComponent },   
  { path: 'profilPage', component: ProfilPageComponent },
  { path: 'registrationPage', component: RegistrationPageComponent },  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule { }
