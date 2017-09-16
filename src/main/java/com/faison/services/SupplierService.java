package com.faison.services;

import com.faison.models.Supplier;
import com.faison.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SupplierService implements IService<Supplier> {

    @Autowired
    private SupplierRepository repository;

    /**
     * Check for the existence of a {@link Supplier} with given id.
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
     * Create and save a new {@link Supplier}.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    public Supplier create(Supplier record) {
        if (exists(record.getId())) {
            return null;
        }
        record.setId(null);
        return repository.save(record);
    }

    /**
     * Find the {@link Supplier} with the given id.
     *
     * @param id The id to be searched for.
     * @return {@link Supplier}
     * the record if found, and null otherwise.
     */
    public Supplier findById(String id) {
        return repository.findOne(id);
    }

    /**
     * Return all available {@link Supplier} in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Supplier> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Supplier> findByNameLike(String name, Pageable pageable) {
        return repository.findByNameLike(name, pageable);
    }

    public Page<Supplier> findByEmailLike(String email, Pageable pageable) {
        return repository.findByEmailLike(email, pageable);
    }

    public Page<Supplier> findByPhoneNumberLike(String phoneNumber, Pageable pageable) {
        return repository.findByPhoneNumberLike(phoneNumber, pageable);
    }

    /**
     * Update an existing {@link Supplier} with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return {@link Supplier}
     * the saved record, if update was successful, or null if the update was unsuccessful.
     */
    public Supplier updateById(String id, Supplier record) {
        if (!exists(id)) {
            return null;
        }
        record.setId(id);
        return repository.save(record);
    }

    /**
     * Delete the {@link Supplier} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    public void deleteById(String id) {
        repository.delete(id);
    }
}
