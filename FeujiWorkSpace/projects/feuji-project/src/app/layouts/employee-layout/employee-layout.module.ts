import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { EmpLayoutRoutes } from "./employee-layout.routing";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(EmpLayoutRoutes),
    FormsModule,
    HttpClientModule,
  ],
  declarations: [
  ]
})

export class EmpLayoutModule {}
