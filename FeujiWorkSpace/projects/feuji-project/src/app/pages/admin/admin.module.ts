import { NgModule } from "@angular/core";
import { AddEmployeeComponent } from "./add-employee/add-employee.component";
import { AdminViewProfileComponent } from "./admin-view-profile/admin-view-profile.component";
import { EmployeeDisplayComponent } from "./employee-display/employee-display.component";
import { CommonModule } from "@angular/common";
import { AdminRoutingModule } from "./admin-routing.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AccountDisplayComponent } from './account-display/account-display.component';
import { AccountAddComponent } from './account-add/account-add.component';
import { UpdateAccountComponent } from './update-account/update-account.component';
import { AddHolidayComponent } from "./add-holiday/add-holiday.component";
import { HolidayComponent } from "./holiday/holiday.component";
import { EditEmployeeComponent } from "./edit-employee/edit-employee.component";
import { MatDialogModule } from "@angular/material/dialog";
import { AdminLandingPageComponent } from "./admin-landing-page/admin-landing-page.component";
import { EditholidayComponent } from './editholiday-component/editholiday-component.component';
import { UpdateEmlpoyeeComponent } from "./update-emlpoyee/update-emlpoyee.component";
import { AccountProjectDisplayComponent } from "./account-project-display/account-project-display.component";
import { AddAccountProjectComponent } from "./add-account-project/add-account-project.component";

import { UpdateProjectComponent } from "./update-project/update-project.component";
import { AddMainSkillComponent } from "./add-main-skill/add-main-skill.component";
import { AddSkillCategoryComponent } from "./add-skill-category/add-skill-category.component";
import { AddSubSkillCategoryComponent } from "./add-sub-skill-category/add-sub-skill-category.component";
import { SkillDisplayComponent } from "../skillgap/skill-display/skill-display.component";
import { MatExpansionModule } from "@angular/material/expansion";



@NgModule({
  declarations: [
    AddEmployeeComponent,
    AdminViewProfileComponent,
    EmployeeDisplayComponent,
    AccountDisplayComponent,
    AccountAddComponent,
    UpdateAccountComponent,
    AddHolidayComponent,
    HolidayComponent,
    EditEmployeeComponent,
    AdminLandingPageComponent,
     EditholidayComponent,
     UpdateEmlpoyeeComponent,
     AccountProjectDisplayComponent,
     AddAccountProjectComponent,
    UpdateProjectComponent,
    AddMainSkillComponent,
    AddSkillCategoryComponent,
    AddSubSkillCategoryComponent,
    SkillDisplayComponent
    ],

  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AdminRoutingModule,
    MatDialogModule,
    MatExpansionModule
  ]
})
export class adminModule { }
