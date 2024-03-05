package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Schedule;
import com.gangE.DongoShop.model.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findAllByUserSchedules(UserSchedule userschedules);


}
