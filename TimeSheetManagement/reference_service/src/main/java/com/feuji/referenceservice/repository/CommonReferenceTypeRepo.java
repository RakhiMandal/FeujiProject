package com.feuji.referenceservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.referenceservice.dto.ReferenceDto;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;

public interface CommonReferenceTypeRepo extends JpaRepository<CommonReferenceTypeEntity,Integer>{

	@Query(value = "select * from common_reference_type where reference_type_name=?",nativeQuery = true)
	public CommonReferenceTypeEntity getByTypeName(String typeName);
	
	@Query("SELECT new com.feuji.referenceservice.dto.ReferenceDto(rd.referenceTypeId, rd.referenceTypeName) " +
            "FROM CommonReferenceTypeEntity rd")
    List<ReferenceDto> findAllReferences();
	
	public CommonReferenceTypeEntity findByReferenceTypeName(String referenceDetailValue);
	
	@Query(value = "select reference_type_name from timesheet_entry_system_db.common_reference_type where reference_type_id=:id ", nativeQuery = true)
	public String getNameById(int id);
	
	@Query(value = "select * from timesheet_entry_system_db.common_reference_type where reference_type_name=?", nativeQuery = true)
	public CommonReferenceTypeEntity getRefByTypeName(String typeName);
}
