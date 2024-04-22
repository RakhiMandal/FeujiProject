package com.feuji.skillgapservice.dto;

public class SkillsBean {
	
	private int skillId;
	private String skillName;
	private String actualCompetency;
	private String expectedCompetency;
	private Integer actualcompetencyLevelId;
	private Integer competencyLevelId;
	private Integer expectedcompetencyLevelId;
	private String skillType;
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
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
	public Integer getCompetencyLevelId() {
		return competencyLevelId;
	}
	public void setCompetencyLevelId(Integer competencyLevelId) {
		this.competencyLevelId = competencyLevelId;
	}
	public Integer getExpectedcompetencyLevelId() {
		return expectedcompetencyLevelId;
	}
	public void setExpectedcompetencyLevelId(Integer expectedcompetencyLevelId) {
		this.expectedcompetencyLevelId = expectedcompetencyLevelId;
	}
	public String getSkillType() {
		return skillType;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	
	public SkillsBean() {
		super();
	}
	public SkillsBean(int skillId, String skillName, String actualCompetency, String expectedCompetency,
			Integer actualcompetencyLevelId, Integer expectedcompetencyLevelId, String skillType) {
		super();
		this.skillId = skillId;
		this.skillName = skillName;
		this.actualCompetency = actualCompetency;
		this.expectedCompetency = expectedCompetency;
		this.actualcompetencyLevelId = actualcompetencyLevelId;
		this.expectedcompetencyLevelId = expectedcompetencyLevelId;
		this.skillType = skillType;
		this.competencyLevelId=this.expectedcompetencyLevelId - this.actualcompetencyLevelId;
	}
	@Override
	public String toString() {
		return "SkillsBean [skillId=" + skillId + ", skillName=" + skillName + ", actualCompetency=" + actualCompetency
				+ ", expectedCompetency=" + expectedCompetency + ", actualcompetencyLevelId=" + actualcompetencyLevelId
				+ ", competencyLevelId=" + competencyLevelId + ", expectedcompetencyLevelId="
				+ expectedcompetencyLevelId + ", skillType=" + skillType + "]";
	}
	
	
	
}
