import { Component, OnInit } from '@angular/core';
import { Employee, EmployeeSaving } from '../../../../models/employee.model';
import { EmployeeService } from '../../../../models/employee.service';
import { AbstractControl, FormControl, ValidatorFn, Validators } from '@angular/forms';
import { SaveEmployee } from '../../../../models/saveemployee.model';
import { SharedService } from '../../../../models/shared.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls:[ './add-employee.component.css']
})
export class AddEmployeeComponent implements OnInit{
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
  public referenceData: SaveEmployee[]=[]
  public employee:any=Employee;
  public emplyoee1:any=[];
  public employmentTypes: SaveEmployee[]=[]
  public selectedEmploymentType: string = '';


  public businessUnitType: any[] = [];
  public statusTypes:any[]=[];

  emp:EmployeeSaving=new EmployeeSaving(0,'','','','','','','',0,new Date(),0,0,0,0,0,new Date(),'',false,'',new Date(),'',new Date);
  employeeTypeReference: any;

  constructor(private empService:EmployeeService,private shared : SharedService) {
    this.getAllReferenceType();
   this.referenceUrl = this.shared.referenceUrl
  }

  ngOnInit() {

   this.fetchReportingManager();
   this.getStatusType();
    this.getBusinessUnitType();
  }

  sendEmployee(): void {
    console.log('Employee to be saved:', this.emp);
    this.empService.getEmployeeDetails().subscribe(data => {
      const existingEmails = data.map((employee: any) => employee.email);
      if (existingEmails.includes(this.emp.email)) {
        Swal.fire('Warning', 'Email already exists', 'warning');
        return;
      }

      this.empService.saveEmployee(this.emp).subscribe({
        next: (res) => {
          console.log('Employee saved:', res);
          Swal.fire('Success', 'Employee saved successfully', 'success');
        },
        error: (error) => {
          console.error('Error saving employee:', error);
          Swal.fire('Error', 'Failed to save employee: ' + error.message, 'error');
        }
      });
    });
  }
  employeeCodeControl = new FormControl('', [Validators.required, this.checkForUniqueEmployeeCode()]);

  checkForUniqueEmployeeCode(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value; 
      const isNotUnique = false;
      return isNotUnique ? { notUnique: true } : null;
    };
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
    this.empService.getReportingManager().subscribe(
        (resp:any) => {
            this.reportingManager=resp;
            console.log("Reporting Manager: ", this.reportingManager);
        },
        (error) => {
            console.error("Error fetching reporting managers:", error);
        }
    );
  }

  onSubmit() {
    const selectedReportingManagerId = (document.getElementById('reportingManager') as HTMLSelectElement).value;
    console.log("Selected Reporting Manager ID: ", selectedReportingManagerId);
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

        this.reportingManagerReference=resp.filter((item:any) => item.referenceTypeName === 'Employee Status').reverse().pop()
        console.log("Employee Status",this.statusReference);
        if (this.statusReference.referenceTypeId) {
            this.fetchStatus(this.statusReference.referenceTypeId)
        }



      })
    }

    

    checkEmailUnique(email: string): void {
      if (email) { 
        this.empService.checkEmployeeEmail(email).subscribe(
          response => {
            if (typeof response === 'boolean') {
              if (response === true) {
                console.log("email is unique ",email);
  
              } else {
                Swal.fire('Warning', 'Email already exists', 'warning');
              }
            } else {
              console.error('Invalid response:', response);
              Swal.fire('Error', 'Failed to check email uniqueness', 'error');
            }
          },
          error => {
            console.error('Error checking email uniqueness:', error);
            Swal.fire('Error', 'Failed to check email uniqueness', 'error');
          }
        );
      }
    }
    getEmployeeDetails(){
      this.empService.getEmployeeDetails().subscribe(data=>{
       console.log(data);
       this.emplyoee1=data;
       console.log(this.emplyoee1);
     })
  }



  getBusinessUnitType() {
    this.empService.getBusinessUnitType().subscribe((businessUnit:any[]) => {
      console.log(businessUnit+":::::::::");
      this.businessUnitType = businessUnit;
    });
  }

  getStatusType() {
    this.empService.getStatusType().subscribe((status:any[]) => {
      console.log(status);
      console.log("status:::::::::");

      this.statusTypes = status;
    });
  }
  }

