package com.feuji.referenceservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.referenceservice.entity.CommonReferenceDetailsEntity;

public interface CommonReferenceDetailsRepo extends JpaRepository<CommonReferenceDetailsEntity, Integer>{
	

	@Query(value ="select reference_details_values,reference_details_id from common_reference_details "
			+ "rd where rd.reference_type_id=(select reference_type_id from common_reference_type"
			+ " where reference_type_name=:typeName)", nativeQuery = true )
	public List<String> getDetailsByTypeName(String typeName);

	
	@Query(value = "select * from timesheet_entry_system_db.common_reference_details where reference_type_id=:reference_type_id and reference_details_values=:reference_details_values ", nativeQuery = true)
	CommonReferenceDetailsEntity findByReferenceDetailValue(
			@Param("reference_details_values") String reference_details_values,
			@Param("reference_type_id") Integer reference_type_id);
	
	public CommonReferenceDetailsEntity findByReferenceDetailValue(String referenceDetailValue);
	
	@Query(value = "SELECT reference_details_id FROM common_reference_details WHERE reference_details_values = ?", nativeQuery = true)
	public int getByName(String name);

	@Query(value = "select reference_details_values from common_reference_details where reference_details_id=:id ", nativeQuery = true)
	public String getNameById(int id);
	
	@Query(value = "select reference_details_values from common_reference_details where "
			+ "reference_type_id=(select reference_type_id from common_reference_type "
			+ "where reference_type_name=:category)", nativeQuery = true)
	public List<String> getCategories(String category);
	
	@Query(value = "select reference_details_values,reference_details_id,rd.reference_type_id "
			+ " from common_reference_details rd where rd.reference_type_id=(select reference_type_id "
			+ " from common_reference_type where reference_type_name=:typeName) and rd.is_deleted=0", nativeQuery = true)
	public List<String> getDetailsByTypeRefName(String typeName);
	
}
