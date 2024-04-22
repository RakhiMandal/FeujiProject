package com.feuji.accountprojectservice.entity;

import java.sql.Timestamp;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Builder
@ToString
@Table(name="common_reference_details")
public class CommonReferenceDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reference_details_id")
	private Integer referenceDetailId;
	
	@Column(name = "reference_details_values")
	private String referenceDetailValue;
	

    @Column(name="reference_type_id")
    private Integer referenceTypeId;
    
//    @OneToOne
//    @JoinColumn(name = "reference_type_id" ,referencedColumnName = "reference_type_id")
//    private CommonReferenceTypeEntity referenceType;

	
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
