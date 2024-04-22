package com.feuji.accountprojectservice.dto;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor

public class UpdateAccountProjectDto {

 private Integer accountProjectId;

	private String projectAId;

	private String projectName;

	private Integer accountId;
	private String accountName;
	  private Integer projectManagerId;

	private String firstName;
	  private Integer priority;
	  private String priorityName;
	  private Integer projectStatus;
	private String projectStatusName;
	
	private float noOfBillingHours;

	private Timestamp formattedPlannedStartDate;


	private Timestamp formattedPlannedEndDate;


	private Timestamp formattedActualStartDate;


	private Timestamp formattedActualEndDate;
	private String uuid;
	private Boolean isDeleted;
	
	public String getPlannedStartDate() {
        return formatDate(formattedPlannedStartDate);
    }

    public String getPlannedEndDate() {
        return formatDate(formattedPlannedEndDate);
    }

    public String getActualStartDate() {
        return formatDate(formattedActualStartDate);
    }

    public String getActualEndDate() {
        return formatDate(formattedActualEndDate);
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timestamp);
    }

	public UpdateAccountProjectDto(Integer accountProjectId, String projectAId, String projectName, Integer accountId,
			String accountName, Integer projectManagerId, String firstName, Integer priority, String priorityName,
			Integer projectStatus, String projectStatusName, float noOfBillingHours,
			Timestamp formattedPlannedStartDate, Timestamp formattedPlannedEndDate, Timestamp formattedActualStartDate,
			Timestamp formattedActualEndDate, String uuid, Boolean isDeleted) {
		super();
		this.accountProjectId = accountProjectId;
		this.projectAId = projectAId;
		this.projectName = projectName;
		this.accountId = accountId;
		this.accountName = accountName;
		this.projectManagerId = projectManagerId;
		this.firstName = firstName;
		this.priority = priority;
		this.priorityName = priorityName;
		this.projectStatus = projectStatus;
		this.projectStatusName = projectStatusName;
		this.noOfBillingHours = noOfBillingHours;
		this.formattedPlannedStartDate = formattedPlannedStartDate;
		this.formattedPlannedEndDate = formattedPlannedEndDate;
		this.formattedActualStartDate = formattedActualStartDate;
		this.formattedActualEndDate = formattedActualEndDate;
		this.uuid = uuid;
		this.isDeleted = isDeleted;
	}

	
}
