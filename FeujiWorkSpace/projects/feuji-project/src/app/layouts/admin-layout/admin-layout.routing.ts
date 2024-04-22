import { Routes } from '@angular/router';

export const AdminLayoutRoutes: Routes = [

  {
    path: "admin",
    loadChildren: () =>
      import("../../pages/admin/admin.module").then((n) => n.adminModule),
  },
];


