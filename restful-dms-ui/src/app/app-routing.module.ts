import {AuthGuard} from './guards/auth.guard';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RouterModule, Routes} from '@angular/router';
import {FileComponent} from "./file/file.component";

const routes: Routes = [];

const appRoutes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'file',
    component: FileComponent,
    canActivate: [AuthGuard]

  },
  // default path for / and unknown paths
  { path: '**', redirectTo: 'home' }
];

export const routing = RouterModule.forRoot(appRoutes);
