package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.LikePost;
import com.gangE.DongoShop.model.Post;
import com.gangE.DongoShop.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {



}
