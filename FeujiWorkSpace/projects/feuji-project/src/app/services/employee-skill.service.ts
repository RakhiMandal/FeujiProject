import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmployeeUiBean } from '../../models/EmployeeSkill.model';
import { SkillCompetency } from '../../models/SkillCompetency.model';
import { EmployeeSkillGet } from '../../models/EmployeeSkillGet.model';
import { SkillNamesDto } from '../../models/SkillNamesDto.model';
import { environment } from '../../environments/environment';
import { Paths } from '../../environments/Api\'s/employee-api-enums';
import { SkillCategoryBean } from '../../models/SkillCategoryBean.model';
import { SubSkillCategoryBean } from '../../models/SubSkillCategoryBean.model';
import { Skill } from '../../models/skill.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeSkillService 
{
  getSkillCategoryIdByName(skillCategory: string): Observable<any>  {
      return this.http.get<any>(`http://localhost:8089/api/referencetype/getref/${skillCategory}`)
  }
  
  
  getSkillCategoryTypeId(selectedsubskillcategory: string): Observable<any> 
  {
    return this.http.get<any>(`http://localhost:8089/api/referencetype/getreference/${selectedsubskillcategory}`)
  }

  
  getSkillNames(skillIds: number[]) {
    return this.http.get<SkillNamesDto[]>(`${environment.SkillService}${Paths.FetchSkillNames}${skillIds}`)
  }

  constructor(private http:HttpClient) { }

  getRoles(selectedTechnicalCate: number):Observable<SkillCompetency[]> {

   return this.http.get<SkillCompetency[]>(`${environment.SkillService}${Paths.FetchRoles}${selectedTechnicalCate}`)
  }
  
  getEmployeSkillsBySkillId(selectedSkillId: number[] ,page:number, size:number,roleName:string):Observable<any>
  {
    return this.http.get<any>(`${environment.SkillService}${Paths.FetchEmployeeSkills}${selectedSkillId}/${page}/${size}/${roleName}`)
  }

  public getAllEmployeeSkills(empMail:string):Observable<any[]>
  {
    return this.http.get<any[]>(`${environment.EmployeeSkillUrl}${Paths.FetchAll}${empMail}`)
  } 

   public getSkillCategories(skillCategoryInput:string):Observable<any[]>
  {
    return this.http.get<any[]>(`${environment.ReferenceServiceUrl}${Paths.Getreference}${skillCategoryInput}`)
  }
  public getSkillCategoryNames():Observable<any[]>
  {
    return this.http.get<any[]>(`http://localhost:8089/api/referencedetails/getreference/Skill Category`)
  }
  getTechnicalCategory(selectedSkillCategory:any):Observable<any[]>{
    return this.http.get<any[]>(`${environment.ReferenceServiceUrl}${Paths.Getreference}${selectedSkillCategory}`); 
  
   }
   getSkills(selectedTechnicalCategory:any):Observable<any[]>{
    return this.http.get<any[]>(`${environment.SkillService}${Paths.GetAllSkills}${selectedTechnicalCategory}`); 
  
   }

   getSkillsForEmployee(selectedTechnicalCategory:any):Observable<any[]>{
   
    return this.http.get<any[]>(`${environment.SkillService}${Paths.GetAllSkillsForEmployee}${selectedTechnicalCategory}`); 
  
   }

   getSkilltype(skillTypeInput:any):Observable<any[]>{
    return this.http.get<any[]>(`${environment.ReferenceServiceUrl}${Paths.Getreference}${skillTypeInput}`); 

   }

   getSkillCompetency(skillCompetencyInput:any):Observable<any[]>{
    return this.http.get<any[]>(`${environment.ReferenceServiceUrl}${Paths.Getreference}${skillCompetencyInput}`); 
   }
 
  postEmployeeSkills(employeeSkill:EmployeeUiBean[]): Observable<EmployeeUiBean[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<EmployeeUiBean[]>(`${environment.EmployeeSkillUrl}${Paths.Save}`,employeeSkill,{headers});
 }

  editEmployeeSkill(editedRow:EmployeeSkillGet,employeeSkillId:any): Observable<any> {
   const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
   return this.http.put<EmployeeSkillGet>(`${environment.EmployeeSkillUrl}${Paths.Update}${employeeSkillId}`,editedRow);
  
 }

 deleteRow(employeeSkillId:any){
  const headers = new HttpHeaders({ 'Content-Type': 'application/json'});
  return this.http.put<EmployeeSkillGet>(`${environment.EmployeeSkillUrl}${Paths.Delete}${employeeSkillId}`,{headers});
 
 }

 deleteSubCategory(subItemreferenceDetailId: any)
  {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json'});
    return this.http.put<any>(`${environment.ReferenceServiceUrl}${Paths.DeleteSubCategory}${subItemreferenceDetailId}`,{headers});
  }

  deleteCategory(categoryName: string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json'});
    return this.http.put<any>(`${environment.ReferenceServiceUrl}${Paths.DeletCategory}${categoryName}`,{headers});  }

 getSkillsByEmployeeId(email: string): Observable<any> {
  return this.http.get<any>(`${environment.SkillSetUrl}${Paths.Fetch}${email}`);
}

getSkillsOfEmployee(email: string, skillcategoryid:number): Observable<any> {
  return this.http.get<any>(`${environment.SkillSetUrl}${Paths.Fetch}${email}/${skillcategoryid}`);
  }


saveSkillCategoryAdmin(newCategory: SkillCategoryBean): Observable<SkillCategoryBean> {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  console.log(newCategory)
  return this.http.post<SkillCategoryBean>(`${environment.ReferenceServiceUrl}${Paths.SaveSkillCategory}`, newCategory,{headers});
}

saveSubSkillCategoryAdmin(newSubCategory: SubSkillCategoryBean): Observable<SubSkillCategoryBean> {
  console.log("service"+newSubCategory.referenceTypeId.referenceTypeId)
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.http.post<SubSkillCategoryBean>('http://localhost:8089/api/referencedetails/addSubSkill', newSubCategory, { headers });
}

subSkillAdmin(newSkill: Skill): Observable<Skill> {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.http.post<Skill>('http://localhost:8087/api/skill/insert', newSkill, { headers });
}




public getTrainingRecommendedEmployees(skillIds:number[]): Observable<any>
{
  return this.http.get<any>(`http://localhost:8087/api/fetchallemployees/${skillIds}`);
}
  
public updateStatusAdmin(skillIds: any, status: any): Observable<any> {
    return this.http.put<any>(`http://localhost:8087/api/skill/updateStatus?skillIds=${skillIds}&status=${status}`,{});
  
}

saveSkillAdmin(newSkill: Skill): Observable<Skill> {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.http.post<Skill>('http://localhost:8087/api/skill/insert', newSkill,{headers});
}
}
