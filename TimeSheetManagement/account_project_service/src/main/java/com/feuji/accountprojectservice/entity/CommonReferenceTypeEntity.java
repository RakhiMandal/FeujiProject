package com.feuji.accountprojectservice.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name ="common_reference_type" )

public class CommonReferenceTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reference_type_id")
	private Integer referenceTypeId;
	
	@Column(name = "reference_type_name")
	private String referenceTypeName;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;

}
