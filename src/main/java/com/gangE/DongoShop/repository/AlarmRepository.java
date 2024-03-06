package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {


    Page<Alarm> findAllByUserIdOrderByCreatedAtDesc(Pageable pageable, int userId);





}
