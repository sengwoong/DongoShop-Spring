package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Alarm;
import com.gangE.DongoShop.model.Exam;
import com.gangE.DongoShop.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {


    Page<Alarm> findAllByUserIdOrderByCreatedAtDesc(Pageable pageable, int userId);





}
