package com.feuji.accountprojectservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.accountprojectservice.entity.AccountProjectTaskEntity;

public interface AccountProjectTaskRepo extends JpaRepository<AccountProjectTaskEntity, Integer> {

}
