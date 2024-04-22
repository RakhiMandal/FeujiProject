import { Routes } from '@angular/router';
export const EmpLayoutRoutes: Routes = [

  {
    path: "employee",
    loadChildren: () =>
      import("../../pages/employees/employee.module").then((n) => n.employeeModule),
  },

]
