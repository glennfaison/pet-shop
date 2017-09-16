package com.faison.services;

import com.faison.models.Address;
import com.faison.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements IService<Address> {

    @Autowired
    private AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    /**
     * Check for the existence of a {@link Address} with given id.
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
     * Create and save a new {@link Address}.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    public Address create(Address record) {
        if (exists(record.getId())) {
            return null;
        }
        record.setId(null);
        return repository.save(record);
    }

    /**
     * Find the {@link Address} with the given id.
     *
     * @param id The id to be searched for.
     * @return {@link Address}
     * the record if found, and null otherwise.
     */
    public Address findById(String id) {
        return repository.findOne(id);
    }

    /**
     * Return all available {@link Address} in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Address> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Address> findByStreetLike(String street, Pageable pageable) {
        return repository.findByStreetLike(street, pageable);
    }

    public Page<Address> findByCityLike(String city, Pageable pageable) {
        return repository.findByCityLike(city, pageable);
    }

    public Page<Address> findByStateLike(String state, Pageable pageable) {
        return repository.findByStateLike(state, pageable);
    }

    public Page<Address> findByCountryLike(String country, Pageable pageable) {
        return repository.findByCountryLike(country, pageable);
    }

    /**
     * Update an existing {@link Address} with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return {@link Address}
     * the saved record, if update was successful, or null if the update was unsuccessful.
     */
    public Address updateById(String id, Address record) {
        if (!exists(id)) {
            return null;
        }
        record.setId(id);
        return repository.save(record);
    }

    /**
     * Delete the {@link Address} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    public void deleteById(String id) {
        repository.delete(id);
    }
}
