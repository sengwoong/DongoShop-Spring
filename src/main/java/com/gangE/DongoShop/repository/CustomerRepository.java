package com.gangE.DongoShop.repository;


import com.gangE.DongoShop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);

    Customer findByName(String email);


    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.authorities WHERE c.id = :customerId")
    Customer findByIdWithAuthorities(@Param("customerId") int id);

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.authorities WHERE c.email = :email")
    Customer findByEmailWithAuthorities(@Param("email") String email);


}
