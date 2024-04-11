package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.NoteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteProductRepository extends JpaRepository<NoteProduct, Long> {
}
