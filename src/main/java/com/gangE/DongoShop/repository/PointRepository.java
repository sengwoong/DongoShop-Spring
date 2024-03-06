package com.gangE.DongoShop.repository;

import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>   {


    Point findByUser(Customer user);
}
