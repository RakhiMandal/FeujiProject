package com.feuji.timesheetentryservice.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component
public class EmployeeDetailsRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Object[]> getAccountManagerDetails(Integer accountId, Integer employeeId) {
		String sqlQuery = "WITH ACCOUNT_RESOURCE_MAPPING_IDS AS (\r\n"
				+ "SELECT DISTINCT ACCOUNT_PROJECT_ID FROM TIMESHEET_ENTRY_SYSTEM_DB.ACCOUNT_RESOURCE_MAPPING WHERE ACCOUNT_ID = ? AND EMPLOYEE_ID=?),\r\n"
				+ "ACCOUNT_PROJECT_MANAGERS_IDS AS (\r\n"
				+ "SELECT AP.PROJECTMANAGER_ID FROM TIMESHEET_ENTRY_SYSTEM_DB.ACCOUNT_PROJECTS AP, ACCOUNT_RESOURCE_MAPPING_IDS ARM\r\n"
				+ " WHERE AP.ACCOUNT_PROJECT_ID = ARM.ACCOUNT_PROJECT_ID),\r\n"
				+ "EMPLOYEE_DETAILS AS (SELECT * FROM TIMESHEET_ENTRY_SYSTEM_DB.EMPLOYEE E,  ACCOUNT_PROJECT_MANAGERS_IDS APM WHERE E.EMPLOYEE_ID = APM.PROJECTMANAGER_ID)\r\n"
				+ "SELECT FIRST_NAME, LAST_NAME, EMAIL FROM EMPLOYEE_DETAILS;";

		Query query = entityManager.createNativeQuery(sqlQuery);
		query.setParameter(1, accountId);
		query.setParameter(2, employeeId);

		// Execute the query and return the result
		return query.getResultList();
	}

	public List<Object[]> getReportingManagerDetails(Integer accountId, Integer employeeId) {
		String sqlQuery = "WITH ACCOUNT_RESOURCE_MAPPING_IDS AS (\r\n"
				+ "SELECT DISTINCT ACCOUNT_PROJECT_ID FROM TIMESHEET_ENTRY_SYSTEM_DB.ACCOUNT_RESOURCE_MAPPING WHERE ACCOUNT_ID = ? AND EMPLOYEE_ID=?),\r\n"
				+ "ACCOUNT_PROJECT_MANAGERS_IDS AS (\r\n"
				+ "SELECT AP.PROJECTMANAGER_ID FROM TIMESHEET_ENTRY_SYSTEM_DB.ACCOUNT_PROJECTS AP, ACCOUNT_RESOURCE_MAPPING_IDS ARM\r\n"
				+ "WHERE AP.ACCOUNT_PROJECT_ID = ARM.ACCOUNT_PROJECT_ID),\r\n"
				+ "EMPLOYEE_DETAILS AS (SELECT * FROM TIMESHEET_ENTRY_SYSTEM_DB.EMPLOYEE E,  ACCOUNT_PROJECT_MANAGERS_IDS APM WHERE E.EMPLOYEE_ID = APM.PROJECTMANAGER_ID),\r\n"
				+ "REPORT_MANAGER_EMPLOYEE_DETAILS AS (SELECT E.* FROM TIMESHEET_ENTRY_SYSTEM_DB.EMPLOYEE E,  EMPLOYEE_DETAILS ED WHERE E.EMPLOYEE_ID = ED.REPORTING_MANAGER_ID) "
				+ "SELECT FIRST_NAME, LAST_NAME, EMAIL FROM REPORT_MANAGER_EMPLOYEE_DETAILS;";

		Query query = entityManager.createNativeQuery(sqlQuery);
		query.setParameter(1, accountId);
		query.setParameter(2, employeeId);

		// Execute the query and return the result
		return query.getResultList();
	}
}


