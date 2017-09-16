package com.faison.services;

import com.faison.models.Order;
import com.faison.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService implements IService<Order> {

    @Autowired
    private OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Check for the existence of a {@link Order} with given id.
     *
     * @param id The id to search for.
     * @return true if such a record exists, and false otherwise.
     */
    public boolean exists(String id) {
        if (id == null) {
            return false;
        }
        return repository.exists(id);
    }

    /**
     * Create and save a new {@link Order}.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    public Order create(Order record) {
        if (exists(record.getId())) {
            return null;
        }
        record.setId(null);
        return repository.save(record);
    }

    /**
     * Find the {@link Order} with the given id.
     *
     * @param id The id to be searched for.
     * @return {@link Order}
     * the record if found, and null otherwise.
     */
    public Order findById(String id) {
        return repository.findOne(id);
    }

    /**
     * Return all available {@link Order} in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Order> findByPlacedOnBefore(Date placedOn, Pageable pageable) {
        return repository.findByPlacedOnBefore(placedOn, pageable);
    }

    public Page<Order> findByPlacedOnAfter(Date placedOn, Pageable pageable) {
        return repository.findByPlacedOnAfter(placedOn, pageable);
    }

    public Page<Order> findByPlacedOnBetween(Date from, Date to, Pageable pageable) {
        return repository.findByPlacedOnBetween(from, to, pageable);
    }

    public Page<Order> findByBuyer_Id(String userId, Pageable pageable) {
        return repository.findByBuyer_Id(userId, pageable);
    }

    /**
     * Update an existing {@link Order} with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return {@link Order}
     * the saved record, if update was successful, or null if the update was unsuccessful.
     */
    public Order updateById(String id, Order record) {
        if (!exists(id)) {
            return null;
        }
        record.setId(id);
        return repository.save(record);
    }

    /**
     * Delete the {@link Order} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    public void deleteById(String id) {
        repository.delete(id);
    }
}