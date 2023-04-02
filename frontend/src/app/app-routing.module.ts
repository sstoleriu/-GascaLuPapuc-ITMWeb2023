import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { ReportsComponent } from './pages/reports/reports.component';
import { authGuard } from './guards/AuthGuard';
import { ROLE } from './enums/ROLE';
const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: LoginComponent},
  {path: 'reports', component: ReportsComponent, canActivate: [authGuard], data: {authorized_roles: [ROLE.USER]}},
  {path: '**', redirectTo: 'reports'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
