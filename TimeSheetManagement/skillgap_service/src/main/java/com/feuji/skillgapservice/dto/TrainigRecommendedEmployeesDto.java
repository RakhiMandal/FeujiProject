package com.feuji.skillgapservice.dto;

public class TrainigRecommendedEmployeesDto {
	private Integer employeeId;
	private String employeeCode;
	private String firstName;
	private String middleName;
	private String lastName;
	private String designition;
	private String email;
	private String skillName;
	private String actualCompetency;
	private String expectedCompetency;
	private Integer actualcompetencyLevelId;
	private Integer expectedcompetencyLevelId;
	private Integer competencyLevelId;
	private String employeeName;

	public TrainigRecommendedEmployeesDto(Integer employeeId, String employeeCode, String firstName, String middleName,
			String lastName, String designition, String email, String skillName, String actualCompetency,
			String expectedCompetency, Integer actualcompetencyLevelId, Integer expectedcompetencyLevelId) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.designition = designition;
		this.email = email;
		this.skillName = skillName;
		this.actualCompetency = actualCompetency;
		this.expectedCompetency = expectedCompetency;
		this.actualcompetencyLevelId = actualcompetencyLevelId;
		this.expectedcompetencyLevelId = expectedcompetencyLevelId;
		this.employeeName = firstName + " " + middleName + " " + lastName;
		this.competencyLevelId = this.expectedcompetencyLevelId - this.actualcompetencyLevelId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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

	public String getDesignition() {
		return designition;
	}

	public void setDesignition(String designition) {
		this.designition = designition;
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

	public String getActualCompetency() {
		return actualCompetency;
	}

	public void setActualCompetency(String actualCompetency) {
		this.actualCompetency = actualCompetency;
	}

	public String getExpectedCompetency() {
		return expectedCompetency;
	}

	public void setExpectedCompetency(String expectedCompetency) {
		this.expectedCompetency = expectedCompetency;
	}

	public Integer getActualcompetencyLevelId() {
		return actualcompetencyLevelId;
	}

	public void setActualcompetencyLevelId(Integer actualcompetencyLevelId) {
		this.actualcompetencyLevelId = actualcompetencyLevelId;
	}

	public Integer getExpectedcompetencyLevelId() {
		return expectedcompetencyLevelId;
	}

	public void setExpectedcompetencyLevelId(Integer expectedcompetencyLevelId) {
		this.expectedcompetencyLevelId = expectedcompetencyLevelId;
	}

	public Integer getCompetencyLevelId() {
		return competencyLevelId;
	}

	public void setCompetencyLevelId(Integer competencyLevelId) {
		this.competencyLevelId = competencyLevelId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}
