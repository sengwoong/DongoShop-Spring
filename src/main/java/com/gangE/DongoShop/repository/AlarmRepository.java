package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Alarm;
import com.gangE.DongoShop.model.Exam;
import com.gangE.DongoShop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {



    List<Alarm> findByUserId(int userId);

    Page<Alarm> findAllByUserIdOrderByCreatedAtDesc(Pageable pageable, int userId);
}
