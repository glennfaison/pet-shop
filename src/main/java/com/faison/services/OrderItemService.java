package com.faison.services;

import com.faison.models.OrderItem;
import com.faison.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService implements IService<OrderItem> {

    @Autowired
    private OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    /**
     * Check for the existence of a {@link OrderItem} with given id.
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
     * Create and save a new {@link OrderItem}.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    public OrderItem create(OrderItem record) {
        if (exists(record.getId())) {
            return null;
        }
        record.setId(null);
        return repository.save(record);
    }

    /**
     * Find the {@link OrderItem} with the given id.
     *
     * @param id The id to be searched for.
     * @return {@link OrderItem}
     * the record if found, and null otherwise.
     */
    public OrderItem findById(String id) {
        return repository.findOne(id);
    }

    /**
     * Return all available {@link OrderItem} in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<OrderItem> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Update an existing {@link OrderItem} with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return {@link OrderItem}
     * the saved record, if update was successful, or null if the update was unsuccessful.
     */
    public OrderItem updateById(String id, OrderItem record) {
        if (!exists(id)) {
            return null;
        }
        record.setId(id);
        return repository.save(record);
    }

    /**
     * Delete the {@link OrderItem} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    public void deleteById(String id) {
        repository.delete(id);
    }
}
