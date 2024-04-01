package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> ,ExamRepositoryCustom{

}
