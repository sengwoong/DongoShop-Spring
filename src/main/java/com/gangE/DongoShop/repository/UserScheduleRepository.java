package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.model.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule, Integer> {

    UserSchedule findByScheduleIdAndUserId(int id, int username);

    List<UserSchedule> findByUserName(String username);
}
