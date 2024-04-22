package com.feuji.employeeservice.dto;


import com.feuji.employeeservice.entity.UserLoginEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {

    private String accessToken;
    private String token;
    private UserLoginEntity userEntity;
}
