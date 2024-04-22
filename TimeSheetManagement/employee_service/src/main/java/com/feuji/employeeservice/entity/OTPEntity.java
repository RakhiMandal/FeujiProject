package com.feuji.employeeservice.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="otp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPEntity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "email")
	private String email;
	@Column(name = "otp")
	private String otp;
	@Column(name = "date_time")
	private Timestamp expirationTime;

}