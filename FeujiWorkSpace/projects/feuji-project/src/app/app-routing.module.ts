import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { EmployeeLayoutComponent } from './layouts/employee-layout/employee-layout.component';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { ManagerLayoutComponent } from './layouts/manager-layout/manager-layout.component';
import { PmoLayoutComponent } from './layouts/pmo-layout/pmo-layout.component';
import { AuthGuard } from './Auth/AuthGuard';
import { AdminGuard } from './Auth/AdminGaurd';
import { EmployeeGuard } from './Auth/EmployeeGaurd';
import { ManagerGuard } from './Auth/ManagerGaurd';

const Routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',

  },
  {
    path: '',
    canActivate:[AuthGuard,AdminGuard],
    component: AdminLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
          import("../app/layouts/admin-layout/admin-layout.module").then((n) => n.AdminLayoutModule),
      }
    ]
  },
  {
    path: '',
    component: AuthLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
          import("../app/layouts/auth-layout/auth-layout.module").then((n) => n.AuthLayoutModule),
      }
    ]
  },
  {
    path: '',
    canActivate:[AuthGuard,EmployeeGuard],
    component: EmployeeLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
         import('../app/layouts/employee-layout/employee-layout.module').then(m => m.EmpLayoutModule)
      }
    ]
  },
  {
    path: '',
    canActivate:[AuthGuard,ManagerGuard],
    component: ManagerLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
         import('../app/layouts/manager-layout/manager-layout.module').then(m => m.ManagerLayoutModule)
      }
    ]
  },
  {
    path: '',
    component: PmoLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
         import('../app/layouts/pmo-layout/pmo-layout.module').then(m => m.PmoLayoutModule)
      }
    ]
  },
]
@NgModule({
  imports: [RouterModule.forRoot(Routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
