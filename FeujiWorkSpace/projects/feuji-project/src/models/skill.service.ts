import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Skill } from './skill.model';

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  constructor(private http: HttpClient) { }
  getSkills():Observable<Skill[]>{
    return this.http.get<Skill[]>(`http://localhost:8087/api/skill/getAll`); 

   }
}
