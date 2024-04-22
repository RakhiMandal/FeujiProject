import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { AdminLayoutRoutes } from "./admin-layout.routing";
import { SkillDisplayComponent } from "../../pages/skillgap/skill-display/skill-display.component";
import { AddMainSkillComponent } from "../../pages/admin/add-main-skill/add-main-skill.component";
import { AddSkillCategoryComponent } from "../../pages/admin/add-skill-category/add-skill-category.component";
import { AddSubSkillCategoryComponent } from "../../pages/admin/add-sub-skill-category/add-sub-skill-category.component";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule

  ],
  declarations: [
  ],

})

export class AdminLayoutModule {}
