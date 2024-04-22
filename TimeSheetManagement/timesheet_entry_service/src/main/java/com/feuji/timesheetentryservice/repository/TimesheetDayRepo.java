package com.feuji.timesheetentryservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.timesheetentryservice.dto.TimeSheetDayHistoryDto;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;

public interface TimesheetDayRepo extends JpaRepository<TimesheetDayEntity, Integer> {
	public TimesheetDayEntity deleteByTimesheetWeekEntityTimesheetWeekId(Integer timesheetWeekId);

	public List<TimesheetDayEntity> findAllByTimesheetWeekEntityTimesheetWeekId(Integer timesheetWeekId);

	@Query("SELECT p FROM TimesheetDayEntity p WHERE p.timesheetWeekEntity.timesheetWeekId = ?1 AND p.attendanceType = ?2 AND p.taskId = ?3")
	List<TimesheetDayEntity> findByWeekIdAndAttendanceTypeAndTaskId(Integer timesheetWeekId, Integer attendanceType,
			Integer taskId);

	@Query("SELECT p FROM TimesheetDayEntity p WHERE p.date = :dateOfWeek")
	TimesheetDayEntity findByDate(@Param("dateOfWeek") Date dateOfWeek);
	
	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimeSheetDayHistoryDto(" +
		       "pdt.timesheetDayId, " +
		       "pdt.date, " +
		       "aptt.taskType, " +
		       "apt.task, " +
		       "COALESCE((CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END),0) AS billingHours, " +
		       "COALESCE((CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END),0) AS nonBillinghours, " +
		       "COALESCE((CASE WHEN crd.referenceDetailValue = 'Leave' THEN (pdt.numberOfHours/8) ELSE 0 END),0) AS leave) " +
		       "FROM TimesheetDayEntity pdt " +
		       " JOIN TimesheetWeekEntity pwt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId "+
		       " JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId " +
			   " JOIN AccountProjectTask apt ON apt.taskId=pdt.taskId "+
		       "JOIN AccountProjectTaskType aptt ON aptt.taskTypeId=apt.taskTypeId "+
		       "WHERE pwt.uuid = :uuid AND pwt.isDeleted=0 AND pdt.isDeleted=0 ")
		List<TimeSheetDayHistoryDto> getTimeSheetDayHistory(@Param("uuid") String uuId);

    @Query( value="select reference_details_id,reference_details_values  FROM common_reference_details rd where rd.reference_type_id=(select reference_type_id FROM common_reference_type WHERE reference_type_name=:minHoursDay) ",nativeQuery=true)
    List<String> getDetailsByTypeName(String minHoursDay);
}
