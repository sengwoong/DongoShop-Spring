package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentCustomerRepository {

     Customer getCurrentCustomer();
}
