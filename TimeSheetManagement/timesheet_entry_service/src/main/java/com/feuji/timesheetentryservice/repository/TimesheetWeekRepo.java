package com.feuji.timesheetentryservice.repository;

import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.timesheetentryservice.dto.AccountProjectResourceMappingDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto;
import com.feuji.timesheetentryservice.dto.TimeSheetHistoryDto;
import com.feuji.timesheetentryservice.dto.TimesheetApprovalSecondDto;
import com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;

import jakarta.transaction.Transactional;
@Transactional
public interface TimesheetWeekRepo extends JpaRepository<TimesheetWeekEntity, Integer> {

	public List<TimesheetWeekEntity> findByWeekStartDateAndAccountIdAndEmployeeId(Date weekStartDate,Integer accountId,Integer employeeId);

	@Query("select new com.feuji.timesheetentryservice.dto.ProjectNameDto( rmap.accountId, rmap.accountProjectId ,tsktp.projectName) "
			+ "				from AccountProjectResourceMappingEntity rmap left join AccountProjectsEntity tsktp on( rmap.accountProjectId=tsktp.accountProjectId) "
			+ "				where rmap.employeeId=:employeeId And rmap.accountId=:accountId")
	public List<ProjectNameDto> getProjectsByEmpId(@Param("employeeId") Integer employeeId,
			@Param("accountId") Integer accountId);

	@Query("select new com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto (aptt.taskTypeId , aptt.taskType)"
			+ "from  AccountProjectResourceMappingEntity armt left join AccountProjectTaskType aptt on\r\n"
			+ "    ( armt.accountProjectId=aptt.accountProjectId  ) "
			+ " where (armt.employeeId=:employeeId and armt.accountProjectId=:accountProjectId)")

	public List<ProjectTaskTypeNameDto> getProjectTaskTypeName(@Param("employeeId") Integer employeeId,
			@Param("accountProjectId") Integer accountProjectId);

	@Query("select  new com.feuji.timesheetentryservice.dto.ProjectTaskDto(aptt.taskId,aptt.task)"
			+ "from AccountProjectTask aptt where aptt.taskTypeId=:taskTypeId")
	public List<ProjectTaskDto> getProjectTask(@Param("taskTypeId") Integer taskTypeId);

	@Query(value = "select account_project_id from project_week_timesheet "
			+ " where  employee_id=? and account_id=? and week_start_date=? ", nativeQuery = true)
	public List<Integer> getprojectIdOfWeek(Integer employeeId, Integer accountId ,Date weekStartDate);

	@Query(value = "select timesheet_week_id from project_week_timesheet\r\n"
			+ "where account_project_id=?1 and week_start_date=?2 and employee_id=?3", nativeQuery = true)
	public Integer getTimesheetweekId(Integer acountProjectId, Date weekStartDate, Integer employeeId);

    @Query("SELECT new com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto( "
            + " pwts.timesheetWeekId, pwts.employeeId, pwts.accountId, pwts.accountProjectId, "
            + " pwts.weekStartDate, pwts.weekEndDate, pwts.weekNumber, pdts.timesheetDayId,  "
            + " aptt.taskId, aptt.taskTypeId, pdts.date, pdts.numberOfHours, pdts.attendanceType,pwts.timesheetStatus ) "
            + " FROM AccountProjectTask aptt "
            + " JOIN TimesheetDayEntity pdts ON aptt.taskId = pdts.taskId "
            + " JOIN TimesheetWeekEntity pwts ON pwts.timesheetWeekId = pdts.timesheetWeekEntity.timesheetWeekId"
            + " WHERE pwts.isDeleted = 0 AND pdts.isDeleted = 0 AND pwts.accountId = :accountId AND pwts.employeeId = :employeeId AND "
            + " (pwts.weekStartDate >= :weekStartDate AND pwts.weekEndDate <= :weekEndDate)")
    List<TimesheetWeekDayDetailDto> findTimesheetDetailsByDateRange(
    		@Param("accountId") Integer accountId,
            @Param("employeeId") Integer employeeId,
            @Param("weekStartDate") Date weekStartDate,
            @Param("weekEndDate") Date weekEndDate 
    );
	@Query("SELECT NEW com.feuji.timesheetentryservice.dto.AccountProjectResourceMappingDto(arm.mappingId,acc.accountId,acc.accountName,ud.userEmpId)"
			+ " FROM AccountProjectResourceMappingEntity arm" + " JOIN AccountEntity acc ON acc.accountId=arm.accountId"
			+ " JOIN UserLoginEntity ud ON ud.userEmpId=arm.employeeId" + " WHERE ud.userEmpId=:userEmpId")
	List<AccountProjectResourceMappingDto> findAccountNameByUserEmpId(@Param("userEmpId") Integer userEmpId);



	
	
	
	
	
	
	
	
	
	
	
	
	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimeSheetHistoryDto(" +
	           "  ep.employeeId ,pwt.uuid,  pwt.weekStartDate, " +
	           "   pwt.weekEndDate, " +
	           "   ap.projectName, " +
	           "   acc.accountName, " +
	           "   crdStatus.referenceDetailValue, " +
	           "ep.firstName, "+
	           "ep.lastName ,"+
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END) AS billingHours, " +
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END) AS nonBillingHours, " +
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8 AS leaveHours) " +
	           "FROM TimesheetWeekEntity pwt " +
	           "JOIN  EmployeeEntity ep On ep.employeeId=pwt.approvedBy "+
	           "JOIN UserLoginEntity ul ON ul.userEmpId=pwt.employeeId "+
	           "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId " +
	           "JOIN AccountEntity acc ON acc.accountId = pwt.accountId " +
	           "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId " +
	           "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId " +
	           "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus " +
	           "WHERE YEAR(pdt.date) = :year " +
	           "AND MONTHNAME(pdt.date) = :month " +
	           "AND acc.accountName = :accountName " +
	           "AND pwt.employeeId=:employeeId AND pwt.isDeleted=0 AND pdt.isDeleted=0 "+
	           "GROUP BY  ep.employeeId ,pwt.uuid,pwt.weekStartDate,ep.lastName,ep.firstName, pwt.weekEndDate, ap.projectName, acc.accountName, crdStatus.referenceDetailValue")
	    List<TimeSheetHistoryDto> getTimeSheetHistory(@Param("month") String month, @Param("year") int year, @Param("accountName") String accountName ,@Param("employeeId") int employeeId);
	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimesheetApprovalSecondDto(" +"ep.employeeId,"+ "pwt.weekStartDate, " + "ep.email, "
			+ "ap.plannedStartDate, " + "ap.plannedEndDate, " + "pwt.weekEndDate, " + "ap.projectName, "
			+ "acc.accountName, " + "ep.employeeCode, " + "ep.designation, " + "ap.projectManagerId, "
			+ "crdStatus.referenceDetailValue, " + "CONCAT(ep.firstName,' ', ep.lastName), "
			+ "SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END), "
			+ "SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END), "
			+ "SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8 , ap.accountProjectId,pwt.weekNumber) "
			+ "FROM TimesheetWeekEntity pwt " + "JOIN EmployeeEntity ep ON ep.employeeId = pwt.employeeId "
			+ "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId "
			+ "JOIN AccountEntity acc ON acc.accountId = pwt.accountId "
			+ "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId "
			+ "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId "
			+ "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus "
			+ "WHERE YEAR(pdt.date) = :year " + "AND MONTHNAME(pdt.date) = :month " + "AND acc.accountId = :accountId "
			+ "GROUP BY ep.employeeId, ep.email,ap.plannedStartDate,ap.plannedEndDate,pwt.uuid, ap.projectManagerId, ep.designation, ep.employeeCode, pwt.weekStartDate, ep.lastName, ep.firstName, pwt.weekEndDate, ap.projectName, acc.accountName, crdStatus.referenceDetailValue, ap.accountProjectId,pwt.weekNumber")
	List<TimesheetApprovalSecondDto> getTimeSheetHistoryForManager(@Param("month") String month, @Param("year") int year,
			@Param("accountId") Integer accountId);
	
	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimeSheetHistoryDto(" +
	           "  ep.employeeId ,  pwt.uuid,pwt.weekStartDate, " +
	           "   pwt.weekEndDate, " +
	           "   ap.projectName, " +
	           "   acc.accountName, " +
	           "   crdStatus.referenceDetailValue, " +
	           "ep.firstName ,"+
	           "ep.lastName ,"+
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END) AS billingHours, " +
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END) AS nonBillingHours, " +
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8 AS leaveHours) " +
	           "FROM TimesheetWeekEntity pwt " +
	           "JOIN  EmployeeEntity ep On ep.employeeId=pwt.approvedBy "+
	           "JOIN UserLoginEntity ul ON ul.userEmpId=pwt.employeeId "+
	           "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId " +
	           "JOIN AccountEntity acc ON acc.accountId = pwt.accountId " +
	           "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId " +
	           "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId " +
	           "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus " +
	           "WHERE YEAR(pdt.date) = :year " +
	           "AND acc.accountName = :accountName " +
	           "AND pwt.employeeId=:employeeId "+
	           "GROUP BY ep.employeeId , pwt.uuid,pwt.weekStartDate, pwt.weekEndDate,ep.lastName,ep.firstName, ap.projectName, acc.accountName, crdStatus.referenceDetailValue")
	    List<TimeSheetHistoryDto> getTimeSheetHistoryByYear( @Param("year") int year, @Param("accountName") String accountName ,@Param("employeeId") int employeeId);

	

	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimeSheetHistoryDto(" +
	           " ep.employeeId , pwt.uuid,  pwt.weekStartDate, " +
	           "   pwt.weekEndDate, " +
	           "   ap.projectName, " +
	           "   acc.accountName, " +
	           "   crdStatus.referenceDetailValue, " +
	           "ep.firstName ,"+
	           "ep.lastName ,"+
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END) AS billingHours, " +
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END) AS nonBillingHours, " +
	           "   SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8 AS leaveHours) " +
	           "FROM TimesheetWeekEntity pwt " +
	           "JOIN  EmployeeEntity ep On ep.employeeId=pwt.approvedBy "+
	           "JOIN UserLoginEntity ul ON ul.userEmpId=pwt.employeeId "+
	           "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId " +
	           "JOIN AccountEntity acc ON acc.accountId = pwt.accountId " +
	           "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId " +
	           "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId " +
	           "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus " +
	           "WHERE YEAR(pdt.date) = :year " +
	           "AND MONTHNAME(pdt.date) = :month " +
	           "AND pwt.employeeId=:employeeId "+
			 "GROUP BY  ep.employeeId ,pwt.uuid,pwt.weekStartDate, pwt.weekEndDate,ep.lastName,ep.firstName, ap.projectName, acc.accountName, crdStatus.referenceDetailValue")
	    List<TimeSheetHistoryDto> getAccountByMonthAndYear(@Param("month") String month, @Param("year") int year,@Param("employeeId") int employeeId);

	@Query(value="select  distinct  year(week_end_date) from project_week_timesheet\r\n"
			+ "where employee_id=:employeeId",nativeQuery = true)
	List<Integer> getYear(@Param("employeeId") int employeeId);

	public List<TimesheetWeekEntity> findByTimesheetStatus(Integer status);
	
	@Modifying
	@Query(value="update project_week_timesheet set timesheet_status=59 where employee_id=:employeeId and account_id=:accountId and week_start_date=:weekStartDate",nativeQuery=true)
	public void updateTimesheetStatus(Integer employeeId,Integer accountId,Date weekStartDate);
	

	@Modifying
	@Query(value="update project_week_timesheet set timesheet_status=60 where employee_id=:employeeId and account_id=:accountId and week_start_date=:weekStartDate",nativeQuery=true)
	public void rejectedTimesheet(Integer employeeId,Integer accountId,Date weekStartDate);
	
	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto(" +
	"ep.employeeId, "+
	        "pwt.weekStartDate, " +
	        "ep.email, " +
	        "ap.plannedStartDate, " +
	        "ap.plannedEndDate, " +
	        "pwt.weekEndDate, " +
	        "ap.projectName, " +
	        "acc.accountName, " +
	        "ep.employeeCode, "+
	        "ep.designation, "+
	        "ap.projectManagerId, "+
	        "crdStatus.referenceDetailValue, "+
	        "CONCAT(ep.firstName,' ', ep.lastName), "+
	        "SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END), "+
	        "SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END), "+
	        "SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8,pwt.accountId) "+
	        "FROM TimesheetWeekEntity pwt "+
	        "JOIN EmployeeEntity ep ON ep.employeeId = pwt.employeeId "+
	        "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId "+
	        "JOIN AccountEntity acc ON acc.accountId = pwt.accountId "+
	        "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId "+
	        "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId "+
	        "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus "+
	        "WHERE YEAR(pdt.date) = :year " +
	        "AND MONTHNAME(pdt.date) = :month " +
	        "AND acc.accountId = :accountId " +
	        "GROUP BY ep.employeeId, ep.email,ap.plannedStartDate,ap.plannedEndDate,pwt.uuid, ap.projectManagerId, ep.designation, ep.employeeCode, pwt.weekStartDate, ep.lastName, ep.firstName, pwt.weekEndDate, ap.projectName, acc.accountName, crdStatus.referenceDetailValue,pwt.accountId")
	List<TimeSheeApprovalDto> getTimeSheetHistory(@Param("month") String month, @Param("year") int year, @Param("accountId") Integer accountId);
}
