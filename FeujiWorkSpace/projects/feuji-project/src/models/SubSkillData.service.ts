import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubSkillData {
  private subSkillDataSubject = new BehaviorSubject<any[]>([]);
  accordionSubData$ = this.subSkillDataSubject.asObservable();

  constructor() {}

  updateAccordionSubData(data: any[]) {
    this.subSkillDataSubject.next(data);
  }
}
