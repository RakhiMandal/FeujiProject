package com.feuji.skillgapservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaginationDto 
{
	private int pageNo;
	private int pageSize;
	private boolean last;
	private boolean first;
	private Long totalRecords;
	private int totalPages;
	private List<EmployeesSkillsListDto> skillList;
}
