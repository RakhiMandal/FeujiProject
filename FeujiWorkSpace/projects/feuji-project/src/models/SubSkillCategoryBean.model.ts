import { ReferenceSkillCategoryBean } from "./ReferenceSkillCategoryBean";

export class SubSkillCategoryBean{
    constructor(
        public referenceDetailValue:string,
        public referenceTypeId:ReferenceSkillCategoryBean,
        public createdBy:string,
        public modifiedBy:string
        
    ){ }
}