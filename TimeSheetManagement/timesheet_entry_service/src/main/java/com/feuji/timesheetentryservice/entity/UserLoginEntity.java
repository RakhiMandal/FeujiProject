package com.feuji.timesheetentryservice.entity;

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
@Table(name = "user_details")
public class UserLoginEntity {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name="user_id")
	    private Integer userId;
	  @Column(name="user_emp_id")
	  private Integer userEmpId;
	  @Column(name="user_name")
	    private String userName;
	  @Column(name="user_password")
	    private String userPassword;
	  @Column(name="designation")
	    private String designation;
	  @Column(name="user_email")
	    private String userEmail;
	  @Column(name="employee_status")
	    private String employeeStatus;
	  @Column(name="is_internal")
	    private boolean isInternal;
	  @Column(name="is_deleted")
	    private boolean isDeleted;
	  @Column(name="uuid")
	    private String uuid;
	  @Column(name="created_by")
	    private String createdBy;
	  @Column(name="created_on")
	    private Timestamp createdOn;
	  @Column(name="modified_by")
	    private String modifiedBy;
	  @Column(name="modified_on")
	    private Timestamp modifiedOn;
	  @Column(name="flag")
	    private String flag;





}
