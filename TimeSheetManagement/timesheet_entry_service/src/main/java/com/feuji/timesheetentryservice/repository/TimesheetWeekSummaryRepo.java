package com.feuji.timesheetentryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.feuji.timesheetentryservice.dto.AccountNameDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto;
import com.feuji.timesheetentryservice.dto.TimesheetApprovalSecondDto;
import com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekSummaryViewEntity;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Transactional
public interface TimesheetWeekSummaryRepo extends JpaRepository<TimesheetWeekSummaryViewEntity, Integer> {

	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimesheetApprovalSecondDto("+"ep.employeeId," + "   pwt.weekStartDate, "
			+ "   ep.email, " + "   ap.plannedStartDate, " + "   ap.plannedEndDate, " + "   pwt.weekEndDate, "
			+ "   ap.projectName, " + "   acc.accountName, " + "   ep.employeeCode, " + "   ep.designation, "
			+ "   ap.projectManagerId, " + "   crdStatus.referenceDetailValue AS timesheetStatus, "
			+ "   CONCAT(ep.firstName,' ', ep.lastName) AS fullName, "
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END) AS billingHours, "
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END) AS nonBillingHours, "
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8 AS leaveHours , ap.accountProjectId,pwt.weekNumber) "
			+ "FROM TimesheetWeekEntity pwt " + "JOIN EmployeeEntity ep ON ep.employeeId=pwt.employeeId "
			+ "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId "
			+ "JOIN AccountEntity acc ON acc.accountId = pwt.accountId "
			+ "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId "
			+ "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId "
			+ "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus "
			+ "WHERE acc.accountId = :accountId "
			+ "AND ap.projectManagerId = :approvedBy " + 
			"GROUP BY ep.employeeId, ep.email, ap.plannedStartDate, ap.plannedEndDate,ap.projectManagerId, ep.designation, ep.employeeCode, pwt.weekStartDate, ep.lastName, ep.firstName, pwt.weekEndDate, ap.projectName, acc.accountName, crdStatus.referenceDetailValue, ap.accountProjectId,pwt.weekNumber")
	List<TimesheetApprovalSecondDto> getAccountProjects(@Param("accountId") Integer accountId,
			@Param("approvedBy") Integer approvedBy);

	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto( "
			+ " pwts.timesheetWeekId , pwts.employeeId, pwts.accountId, pwts.accountProjectId, "
			+ " pwts.weekStartDate, pwts.weekEndDate, pwts.weekNumber, pdts.timesheetDayId,  "
			+ " aptt.taskId, aptt.taskTypeId, pdts.date, pdts.numberOfHours, pdts.attendanceType ) "
			+ " FROM AccountProjectTask aptt " + " JOIN TimesheetDayEntity pdts ON aptt.taskId = pdts.taskId "
			+ " JOIN TimesheetWeekEntity pwts ON pwts.timesheetWeekId = pdts.timesheetWeekEntity.timesheetWeekId ")
	public List<TimesheetWeekDayDetailDto> getimesheetWeekDayDetailDto();

	@Query("SELECT p FROM TimesheetWeekSummaryViewEntity p "
			+ "WHERE p.approvedBy = :approvedBy and p.accountId=:accountId "
			+ "AND p.accountProjectId = :accountProjectId " + "AND p.weekNumber = :weekNumber")
	List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager(@Param("approvedBy") Integer approvedBy,
			@Param("accountId") Integer accountId, @Param("accountProjectId") Integer accountProjectId,
			@Param("weekNumber") Integer weekNumber

	);

	@Query("SELECT p FROM TimesheetWeekSummaryViewEntity p "
			+ "WHERE p.approvedBy = :approvedBy AND p.accountId = :accountId "
			+ "AND FUNCTION('month', p.weekStartDate) = :month " + "AND FUNCTION('month', p.weekEndDate) = :month")
	List<TimesheetWeekSummaryViewEntity> findByApprovedByAndAccountIdAndMonth(@Param("approvedBy") Integer approvedBy,
			@Param("accountId") Integer accountId, @Param("month") Integer month);

	@Query("SELECT DISTINCT NEW com.feuji.timesheetentryservice.dto.AccountNameDto(a.accountId, a.accountName) FROM TimesheetWeekEntity pwt JOIN AccountEntity a ON pwt.accountId = a.accountId WHERE pwt.approvedBy = :approvedBy ")
	public List<AccountNameDto> getAccounts(@Param("approvedBy") String approvedBy);

	@Query("SELECT SUM(totalBillingHours + totalNonBillingHours + totalLeaveHours) "
			+ "FROM TimesheetWeekSummaryViewEntity "
			+ "WHERE employeeId = :employeeId AND accountProjectId = :accountProjectId and weekNumber=:weekNumber")
	public Integer getTotalHours(@Param("employeeId") Integer employeeId,
			@Param("accountProjectId") Integer accountProjectId, @Param("weekNumber") Integer weekNumber);

	@Query("SELECT p FROM TimesheetWeekSummaryViewEntity p "
			+ "WHERE p.approvedBy = :approvedBy and p.accountId=:accountId " + "AND p.weekNumber = :weekNumber")
	List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager(@Param("approvedBy") Integer approvedBy,
			@Param("accountId") Integer accountId,

			@Param("weekNumber") Integer weekNumber

	);

	@Query("SELECT DISTINCT NEW com.feuji.timesheetentryservice.dto.AccountNameDto(a.accountId, a.accountName) FROM TimesheetWeekEntity pwt JOIN AccountEntity a ON pwt.accountId = a.accountId WHERE pwt.approvedBy = :approvedBy ")
	public List<AccountNameDto> getAccounts(@Param("approvedBy") Integer approvedBy);

	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto("  + "   pwt.weekStartDate, "
			+ "   ep.email, " + "   ap.plannedStartDate, " + "   ap.plannedEndDate, " + "   pwt.weekEndDate, "
			+ "   ap.projectName, " + "   acc.accountName, " + "   ep.employeeCode, " + "   ep.designation, "
			+ "   ap.projectManagerId, " + "   crdStatus.referenceDetailValue AS timesheetStatus, "
			+ "   CONCAT(ep.firstName,' ', ep.lastName) AS fullName,"
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END) AS billingHours, "
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END) AS nonBillingHours, "
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8 AS leaveHours) "
			+ "FROM TimesheetWeekEntity pwt " + "JOIN EmployeeEntity ep ON ep.employeeId=pwt.employeeId "
			+ "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId "
			+ "JOIN AccountEntity acc ON acc.accountId = pwt.accountId "
			+ "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId "
			+ "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId "
			+ "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus "
			+ "WHERE YEAR(pdt.date) = :year " +

			"AND acc.accountId = :accountId " + "AND ap.projectManagerId = :projectManagerId "
			+ "GROUP BY ep.email, ap.plannedStartDate, ap.plannedEndDate, pwt.uuid, ap.projectManagerId, ep.designation, ep.employeeCode, pwt.weekStartDate, ep.lastName, ep.firstName, pwt.weekEndDate, ap.projectName, acc.accountName, crdStatus.referenceDetailValue")
	List<TimeSheeApprovalDto> getTimeSheetApproval(@Param("projectManagerId") Integer projectManagerId,
			@Param("year") Integer year, @Param("accountId") Integer accountId);

	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimesheetApprovalSecondDto(" +"ep.employeeId," + "   pwt.weekStartDate, "
			+ "   ep.email, " + "   ap.plannedStartDate, " + "   ap.plannedEndDate, " + "   pwt.weekEndDate, "
			+ "   ap.projectName, " + "   acc.accountName, " + "   ep.employeeCode, " + "   ep.designation, "
			+ "   ap.projectManagerId, " + "   crdStatus.referenceDetailValue, "
			+ "   CONCAT(ep.firstName,' ', ep.lastName), "
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END), "
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END), "
			+ "   SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8,ap.accountProjectId,pwt.weekNumber) "
			+ "FROM TimesheetWeekEntity pwt " + "JOIN EmployeeEntity ep ON ep.employeeId = pwt.employeeId "
			+ "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId "
			+ "JOIN AccountEntity acc ON acc.accountId = pwt.accountId "
			+ "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId "
			+ "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId "
			+ "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus "
			+ "WHERE YEAR(pdt.date) = :year " + "AND MONTHNAME(pdt.date) = :month " + "AND acc.accountId = :accountId "
			+ "AND ep.employeeId = :employeeId " + "AND ap.projectManagerId = :projectManagerId "
			+ "GROUP BY ep.employeeId,ep.email,ap.plannedStartDate,ap.plannedEndDate,pwt.uuid, ap.projectManagerId, ep.designation, ep.employeeCode, pwt.weekStartDate, ep.lastName, ep.firstName, pwt.weekEndDate, ap.projectName, acc.accountName, crdStatus.referenceDetailValue,ap.accountProjectId,pwt.weekNumber")
	List<TimesheetApprovalSecondDto> getTimeSheetApprovalByEmployeeId(@Param("projectManagerId") int projectManagerId,
			@Param("month") String month, @Param("year") Integer year, @Param("accountId") Integer accountId,
			@Param("employeeId") Integer employeeId);

	List<TimesheetWeekSummaryViewEntity> findByApprovedBy(Integer approvedBy);
}