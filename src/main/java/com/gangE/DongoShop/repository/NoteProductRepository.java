package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Note;
import com.gangE.DongoShop.model.NoteProduct;
import com.gangE.DongoShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteProductRepository extends JpaRepository<NoteProduct, Long> {
    Optional<NoteProduct> findByNoteAndProduct(Note note, Product product);
}
