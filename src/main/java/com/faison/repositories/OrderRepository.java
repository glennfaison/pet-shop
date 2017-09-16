package com.faison.repositories;

import com.faison.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {
    Page<Order> findByPlacedOnBefore(Date placedOn, Pageable pageable);

    Page<Order> findByPlacedOnAfter(Date placedOn, Pageable pageable);

    Page<Order> findByPlacedOnBetween(Date from, Date to, Pageable pageable);

    Page<Order> findByBuyer_Id(String userId, Pageable pageable);
}
