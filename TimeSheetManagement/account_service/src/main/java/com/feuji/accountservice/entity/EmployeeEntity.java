package com.feuji.accountservice.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "employee_code", unique = true)
    private String employeeCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "image")
    private String image;

    @Column(name = "designation")
    private String designation;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "date_of_joining")
    private Timestamp dateOfJoining;

    @Column(name = "reporting_manager_id")
    private Integer reportingManagerId;

    @Column(name = "employment_type")
    private Integer employmentType;

    @Column(name = "status")
    private Integer status;

    @Column(name = "delivery_unit_id")
    private Integer deliveryUnitId;

    @Column(name = "business_unit_id")
    private Integer businessUnitId;

    @Column(name = "exit_date")
    private Timestamp exitDate;

    @Column(name = "exit_remarks")
    private String exitRemarks;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private Timestamp modifiedOn;

	

}