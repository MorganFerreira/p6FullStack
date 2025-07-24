import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './features/auth/components/login/login.component';
import { RegisterComponent } from './features/auth/components/register/register.component';
import { StoriesComponent } from './features/stories/components/stories/stories.component';
import { ThemesComponent } from './features/theme/components/themes/themes.component';
import { ProfilComponent } from './features/profil/profil.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'stories', component: StoriesComponent },
    { path: 'themes', component: ThemesComponent },
    { path: 'profile', component: ProfilComponent },
    { path: '**', redirectTo: '404' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutes { }
