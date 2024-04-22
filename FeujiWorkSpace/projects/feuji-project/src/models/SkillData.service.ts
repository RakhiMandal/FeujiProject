import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SkillData {
  private skillDataSubject = new BehaviorSubject<any[]>([]);
  accordionSubData$ = this.skillDataSubject.asObservable();

  constructor() {}

  updateAccordionSubData(data: any[]) {
    this.skillDataSubject.next(data);
  }
}