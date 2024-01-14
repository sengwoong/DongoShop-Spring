package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer findCustomerByEmail(String email) {
        System.out.println("Customer not found for email: " + email);

        Customer customer = customerRepository.findByEmail(email);
        System.out.println("Customer not found for email: " + customer);


        return customer;
    }

    public void saveCustomer(Customer customer) {
        // 추가적인 로직이 필요한 경우 여기에 구현

        customerRepository.save(customer);
    }

}
