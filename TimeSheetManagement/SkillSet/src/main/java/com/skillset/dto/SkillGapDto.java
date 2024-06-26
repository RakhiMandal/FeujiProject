package com.skillset.dto;

public class SkillGapDto {

	private String firstName;
	private String middleName;
	private String lastName;
	private Integer employeeId;
	private String email;
	private String designation;
	private String skillName;
	private String description;
	private Integer exCompetencyLevelId;
	private Integer acCompetencyLevelId;
	private String exReferenceDetailsValues;
	private String acReferenceDetailsValues;
	private String skillCategoryName;
	private String subSkillCategoryName;

	public SkillGapDto(String firstName, String middleName, String lastName, Integer employeeId, String email,
			String designation, String skillName, String description, Integer exCompetencyLevelId,
			Integer acCompetencyLevelId, String exReferenceDetailsValues, String acReferenceDetailsValues,
			String skillCategoryName, String subSkillCategoryName) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.employeeId = employeeId;
		this.email = email;
		this.designation = designation;
		this.skillName = skillName;
		this.description = description;
		this.exCompetencyLevelId = exCompetencyLevelId;
		this.acCompetencyLevelId = acCompetencyLevelId;
		this.exReferenceDetailsValues = exReferenceDetailsValues;
		this.acReferenceDetailsValues = acReferenceDetailsValues;
		this.skillCategoryName = skillCategoryName;
		this.subSkillCategoryName = subSkillCategoryName;
		this.employeeName = firstName + " " + middleName + " " + lastName;
	}

	private String employeeName;

	public SkillGapDto() {
		super();
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getExCompetencyLevelId() {
		return exCompetencyLevelId;
	}

	public void setExCompetencyLevelId(Integer exCompetencyLevelId) {
		this.exCompetencyLevelId = exCompetencyLevelId;
	}

	public Integer getAcCompetencyLevelId() {
		return acCompetencyLevelId;
	}

	public void setAcCompetencyLevelId(Integer acCompetencyLevelId) {
		this.acCompetencyLevelId = acCompetencyLevelId;
	}

	public String getSkillCategoryName() {
		return skillCategoryName;
	}

	public void setSkillCategoryName(String skillCategoryName) {
		this.skillCategoryName = skillCategoryName;
	}

	public String getSubSkillCategoryName() {
		return subSkillCategoryName;
	}

	public void setSubSkillCategoryName(String subSkillCategoryName) {
		this.subSkillCategoryName = subSkillCategoryName;
	}

	public String getExReferenceDetailsValues() {
		return exReferenceDetailsValues;
	}

	public void setExReferenceDetailsValues(String exReferenceDetailsValues) {
		this.exReferenceDetailsValues = exReferenceDetailsValues;
	}

	public String getAcReferenceDetailsValues() {
		return acReferenceDetailsValues;
	}

	public void setAcReferenceDetailsValues(String acReferenceDetailsValues) {
		this.acReferenceDetailsValues = acReferenceDetailsValues;
	}
}