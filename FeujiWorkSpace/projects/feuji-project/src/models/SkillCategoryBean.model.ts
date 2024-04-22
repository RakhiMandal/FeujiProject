import { ReferenceSkillCategoryBean } from "./ReferenceSkillCategoryBean";


export class SkillCategoryBean {
    constructor(
        public referenceDetailValue: string,
        public referenceTypeId: ReferenceSkillCategoryBean,
        public isDeleted:number,
        public createdBy:string,
        public modifiedBy:string
    ) { }
}