import { Routes } from '@angular/router';


export const ManagerLayoutRoutes: Routes = [
  {
    path: "manager",
    loadChildren: () =>
      import("../../pages/project-manager/project-manager.module")
    .then((n) => n.projectManagerModule),
  },
]
