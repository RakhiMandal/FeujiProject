import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../../../../models/employee.service';
import { EmployeeSaving } from '../../../../models/employee.model';
import { SharedService } from '../../../../models/shared.service';
import { SaveEmployee } from '../../../../models/saveemployee.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-emlpoyee',
  templateUrl: './update-emlpoyee.component.html',
  styleUrl: './update-emlpoyee.component.css'
})
export class UpdateEmlpoyeeComponent implements OnInit {
  constructor(private ref:ActivatedRoute,private empService: EmployeeService,private shared : SharedService,private activatedRoute: ActivatedRoute){
    this.referenceUrl = this.shared.referenceUrl
  }
  public empuuId:string="";
  public emp:any;
  public emp1:any;
  referenceUrl:string;
  genderReference:any
  gender:any
  businessUnitReference:any
  businessUnit:any
  deliveryUnitReference:any
  deliveryUnit:any
  statusReference:any
  status:any
  reportingManager:any
  reportingManagerReference:any
  employeeTypeReference: any;
  public employmentTypes: SaveEmployee[]=[]

  public employee:any=EmployeeSaving;

  ngOnInit(): void {
    console.log(history.state);
    
    const employee = history.state.employee;
    console.log(employee);
    
    this.empuuId  = history.state.employee.uuid;
    console.log(this.empuuId);
    
    this.send(this.empuuId);
    this.getAllReferenceType();
    this.fetchReportingManager();

   
  }


send(empuuId:string){

  this.empService.getEmployeeDetailByUuId(this.empuuId).subscribe(
  (items)=>{
  this.emp=items[0];
  
  console.log(this.emp);
  
  
  }

)
}
updateEmployee(emp: any, uuid: any, employeeId: number,isDeleted:boolean) {
  console.log("uuid................" + uuid);
  
  emp.uuid = uuid;
  console.log("uuid......................."+emp.uuid);
  
  emp.employeeId = employeeId;
  emp.isDeleted=isDeleted;
  console.log(this.emp);
  console.log(uuid);
  console.log(emp);

  this.empService.getUpdateEmployee(emp).subscribe(
    (res) => {
      this.employee = res;
      Swal.fire({
        icon: 'success',
        title: 'Employee Updated Successfully',
        text: emp.firstName + ' has been updated!',
      });
    },
    (error) => {
  
    }
  );
}
fetchEmploymentTypes(referenceTypeId: number): void {
    this.empService.getByReferenceTypeId(referenceTypeId)
      .subscribe((data: SaveEmployee[]) => {
        this.employmentTypes = data;

      });
  }

  fetchGender(referenceTypeId: number): void {
    this.empService.getByReferenceTypeId(referenceTypeId)
      .subscribe((data: SaveEmployee[]) => {
        this.gender = data;
      });
  }

  fetchBusinessUnit(referenceTypeId: number): void {
    this.empService.getByReferenceTypeId(referenceTypeId)
      .subscribe((data: SaveEmployee[]) => {
        this.businessUnit = data;
      });
  }

  fetchDeliveryUnit(referenceTypeId: number): void {
    this.empService.getByReferenceTypeId(referenceTypeId)
      .subscribe((data: SaveEmployee[]) => {
        this.deliveryUnit = data;
      });
  }

  fetchStatus(referenceTypeId: number): void {
    this.empService.getByReferenceTypeId(referenceTypeId)
      .subscribe((data: SaveEmployee[]) => {
        this.status = data;
      });
  }

  fetchReportingManager(){
    this.empService.getReportingManager().subscribe((resp:any)=>{
      this.reportingManager=resp;
      console.log("RM: ",this.reportingManager);
    })
  }

  getAllReferenceType(){
    this.empService.getAllReferenceType().subscribe((resp:any)=>{
      this.employeeTypeReference=resp.filter((item:any) => item.referenceTypeName === 'Employee Type').reverse().pop()
        console.log("employee Type",this.employeeTypeReference);
        if (this.employeeTypeReference.referenceTypeId) {
            this.fetchEmploymentTypes(this.employeeTypeReference.referenceTypeId)
        }

        this.genderReference=resp.filter((item:any) => item.referenceTypeName === 'Gender').reverse().pop()
        console.log("Gender",this.genderReference);
        if (this.genderReference.referenceTypeId) {
            this.fetchGender(this.genderReference.referenceTypeId)
        }

        this.businessUnitReference=resp.filter((item:any) => item.referenceTypeName === 'Business Unit').reverse().pop()
        console.log("Business Unit",this.businessUnitReference);
        if (this.businessUnitReference.referenceTypeId) {
            this.fetchBusinessUnit(this.businessUnitReference.referenceTypeId)
        }

        this.deliveryUnitReference=resp.filter((item:any) => item.referenceTypeName === 'Delivery Unit').reverse().pop()
        console.log("Delivery Unit",this.deliveryUnitReference);
        if (this.deliveryUnitReference.referenceTypeId) {
            this.fetchDeliveryUnit(this.deliveryUnitReference.referenceTypeId)
        }
        this.statusReference=resp.filter((item:any) => item.referenceTypeName === 'Employee Status').reverse().pop()
        console.log("Employee Status",this.statusReference);
        if (this.statusReference.referenceTypeId) {
            this.fetchStatus(this.statusReference.referenceTypeId)
        }

       
      })
    }
}