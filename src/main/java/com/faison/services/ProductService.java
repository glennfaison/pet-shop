package com.faison.services;

import com.faison.models.Product;
import com.faison.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Check for the existence of a {@link Product} with given id.
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
     * Create and save a new {@link Product}.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    public Product create(Product record) {
        if (exists(record.getId())) {
            return null;
        }
        record.setId(null);
        return repository.save(record);
    }

    /**
     * Find the {@link Product} with the given id.
     *
     * @param id The id to be searched for.
     * @return {@link Product}
     * the record if found, and null otherwise.
     */
    public Product findById(String id) {
        return repository.findOne(id);
    }

    /**
     * Return all available {@link Product} in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Return all {@link Product} in pages, with similar names.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Product> findByNameLike(String name, Pageable pageable) {
        return repository.findByNameLike(name, pageable);
    }

    /**
     * Return all {@link Product} in pages, with the given categoryId.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Product> findByCategoryId(String categoryId, Pageable pageable) {
        return repository.findByCategoryId(categoryId, pageable);
    }

    /**
     * Return all {@link Product} with similar descriptions, in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Product> findByDescriptionLike(String description, Pageable pageable) {
        return repository.findByDescriptionLike(description, pageable);
    }

    /**
     * Return all {@link Product} with the given unitPrice, in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Product> findByUnitPrice(Double unitPrice, Pageable pageable) {
        return repository.findByUnitPrice(unitPrice, pageable);
    }

    /**
     * Return all {@link Product} in pages, with their unitPrice less than the specified.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Product> findByUnitPriceLessThan(Double unitPrice, Pageable pageable) {
        return repository.findByUnitPriceLessThan(unitPrice, pageable);
    }

    /**
     * Return all {@link Product} in pages, whose unitPrice is greater than the specified value.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Product> findByUnitPriceGreaterThan(Double unitPrice, Pageable pageable) {
        return repository.findByUnitPriceGreaterThan(unitPrice, pageable);
    }

    /**
     * Return all {@link Product} in pages, whose unitPrice falls between the specified values.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Product> findByUnitPriceBetween(Double from, Double to, Pageable pageable) {
        return repository.findByUnitPriceBetween(from, to, pageable);
    }

    /**
     * Update an existing {@link Product} with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return {@link Product}
     * the saved record, if update was successful, or null if the update was unsuccessful.
     */
    public Product updateById(String id, Product record) {
        if (!exists(id)) {
            return null;
        }
        record.setId(id);
        return repository.save(record);
    }

    /**
     * Delete the {@link Product} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    public void deleteById(String id) {
        repository.delete(id);
    }
}