import { CommonModule, DatePipe } from "@angular/common";

import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { EmployeeRoutingModule } from "./employee-routing.module";
import { NgModule } from "@angular/core";
import { EmpViewProfileComponent } from "./emp-view-profile/emp-view-profile.component";
import { TimesheetHomeComponent } from "./timesheet-home/timesheet-home.component";
import { HolidayComponent } from "./holiday/holiday.component";
import { TimesheetHistoryComponent } from "./timesheet-history/timesheet-history.component";
import { TimesheetDayhistoryComponent } from "./timesheet-dayhistory/timesheet-dayhistory.component";
import { EmployeeLandingPageComponent } from './employee-landing-page/employee-landing-page.component';
import { EmployeeSkillDisplayComponent } from "../skillgap/employee-skill-display/employee-skill-display.component";
import { EmployeeSkillGapComponent } from "../skillgap/employee-skill-gap/employee-skill-gap.component";

import { MatTabsModule } from "@angular/material/tabs";
import { MatCardModule } from "@angular/material/card";
import { TrainingRecommendationComponent } from "../skillgap/training-recommendation/training-recommendation.component";

@NgModule({
  declarations: [
    TimesheetHistoryComponent,
    EmpViewProfileComponent,
    TimesheetHomeComponent,
    HolidayComponent,
    TimesheetDayhistoryComponent,
    EmployeeLandingPageComponent,
    EmployeeSkillDisplayComponent,
    EmployeeSkillGapComponent,
    TrainingRecommendationComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    EmployeeRoutingModule,
    MatTabsModule,
    MatCardModule
  ],
  providers:[
    DatePipe
  ]
})
export class employeeModule { }
