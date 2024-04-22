package com.feuji.timesheetentryservice.repository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.feuji.timesheetentryservice.entity.HolidayEntity;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Integer> {
@Modifying
@Query(value="update holiday set is_deleted=1 where holiday_id=:holidayId",nativeQuery=true)
void updateIsDeleted(Integer holidayId);


@Query(value = "SELECT * FROM holiday WHERE YEAR(holiday_date) LIKE %?%", nativeQuery = true)
List<HolidayEntity> findHolidaysByYear(int year);

List<HolidayEntity> findByHolidayNameOrHolidayDate(String name,LocalDate localDate);
}