package com.feuji.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployee {
	private String firstName;
	private String designation;
	private Integer employeeId;

}
