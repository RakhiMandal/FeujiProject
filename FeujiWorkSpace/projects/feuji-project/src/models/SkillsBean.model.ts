export class SkillsBean {
    constructor(
        public skillId: number,
        public skillName: string,
        public actualCompetency:string,
        public expectedCompetency:string,
        public actualcompetencyLevelId:number,
        public competencyLevelId: number,
        public expectedcompetencyLevelId:number,
        public skillType:string
    ){}
  }