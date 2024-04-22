import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { PmoLayoutRoutes } from "./pmo-layout.routing";


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(PmoLayoutRoutes),
    FormsModule,
    HttpClientModule,


  ],
  declarations: [
  ]
})

export class PmoLayoutModule {}
