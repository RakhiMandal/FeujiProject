import { CommonModule, DatePipe } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ManagerViewProfileComponent } from "./manager-view-profile/manager-view-profile.component";
import { TimesheetapproveComponent } from "./timesheetapprove/timesheetapprove.component";
import { TimesheetHomeComponent } from "./timesheet-home/timesheet-home.component";
import { TimesheetHistoryComponent } from "./timesheet-history/timesheet-history.component";
import { ProjectManagerRoutingModule } from "./project-manager-routing.module";
import { DailyStatusComponent } from "./daily-status/daily-status.component";
import { ManagerLandingPageComponent } from './manager-landing-page/manager-landing-page.component';
import { TimesheetDayhistoryComponent } from "./timesheet-dayhistory/timesheet-dayhistory.component";
import { HolidayComponent } from "./holiday/holiday.component";
import { SkillgapDisplayComponent } from "../skillgap/skillgap-display/skillgap-display.component";
import { ManagerRecommendedTrainingComponent } from "../skillgap/manager-recommended-training/manager-recommended-training.component";
import { MatHeaderCell, MatRow, MatTableModule } from "@angular/material/table";
import { MatListModule } from "@angular/material/list";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatCardModule } from "@angular/material/card";
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatRadioModule } from "@angular/material/radio";
import { MatExpansionModule } from "@angular/material/expansion";


@NgModule({
  declarations: [
    ManagerViewProfileComponent,
    TimesheetapproveComponent,
    TimesheetHomeComponent,
    TimesheetHistoryComponent,
    DailyStatusComponent,
    ManagerLandingPageComponent,
    TimesheetDayhistoryComponent,
    HolidayComponent,
    SkillgapDisplayComponent,
    ManagerRecommendedTrainingComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    ProjectManagerRoutingModule,
    MatHeaderCell,
    MatRow,
    MatTableModule,
    MatListModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    MatTooltipModule,
    MatRadioModule,
    MatExpansionModule,
  ],
  providers:[
    DatePipe
  ]
})
export class projectManagerModule { }
