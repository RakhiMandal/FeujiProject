import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ManagerLayoutRoutes } from "./manager-layout.routing";
import { FormsModule } from "@angular/forms";


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(ManagerLayoutRoutes),
    FormsModule,
    HttpClientModule,
  ],
  declarations: [
  ]
})

export class ManagerLayoutModule {}
